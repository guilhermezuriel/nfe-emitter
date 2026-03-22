# NF-e Processing Platform — Contexto Completo (Clean Architecture)

> **Documento de contexto para retomar desenvolvimento em outra sessão Claude.**
> Fase 1 concluída. Próximo passo: refatorar código existente para Clean Architecture + iniciar Fase 2.

---

## 1. Visão Geral

Plataforma de microsserviços para emissão, processamento em lote e exportação em PDF de Notas Fiscais Eletrônicas. Cada microsserviço segue **Clean Architecture (Hexagonal/Ports & Adapters)**.

**Stack**: Spring Boot 3.4.x, Java 17 (Records), Apache Kafka, Istio, Spring Batch, JasperReports, PostgreSQL, MinIO, Prometheus, Grafana, Jaeger.

---

## 2. Clean Architecture — Regras do Projeto

### Princípio central
As dependências apontam sempre para dentro. O domínio não conhece frameworks. A aplicação orquestra use cases. A infraestrutura implementa os ports.

### As 4 camadas (de dentro para fora)

**1. domain** — Entidades de negócio puras, value objects, enums, regras de domínio, exceções de negócio. Zero dependências de framework (sem Spring, sem JPA, sem Jackson). Usa apenas Java puro.

**2. application** — Use cases (um por operação de negócio), ports (interfaces que o domínio precisa do mundo externo: repositórios, publicadores de evento, serviços de storage). Os use cases orquestram entidades de domínio e chamam ports.

**3. infrastructure** — Implementações dos ports: JPA repositories (adapter out), Kafka producer (adapter out), MinIO client (adapter out), Flyway migrations, configurações Spring.

**4. adapter.in** — Pontos de entrada: REST controllers, Kafka consumers/listeners, Spring Batch job configs, schedulers. Chama os use cases da camada application.

### Regra de dependência
```
adapter.in → application → domain ← infrastructure
                ↑                        │
                └── ports (interfaces) ───┘
```

- `domain` NÃO importa nada de `application`, `infrastructure` ou `adapter.in`
- `application` importa `domain` e define ports (interfaces)
- `infrastructure` importa `application` (para implementar ports) e `domain`
- `adapter.in` importa `application` (para chamar use cases)

### Convenções de nomenclatura
- Use cases: verbo no infinitivo — `EmitirNfeUseCase`, `ProcessarImpostosUseCase`, `GerarDanfeUseCase`
- Ports (interfaces): sufixo descritivo — `NfeRepository`, `NfeEventPublisher`, `PdfStorageGateway`
- Adapters out: prefixo de tecnologia — `JpaNfeRepository`, `KafkaNfeEventPublisher`, `MinIoPdfStorageGateway`
- Adapters in: sufixo de protocolo — `NfeRestController`, `NfeKafkaListener`, `NfeBatchJobConfig`

---

## 3. Estrutura de Pacotes por Microsserviço

Cada microsserviço segue esta estrutura. O exemplo abaixo usa `nfe-emissao-service`:

```
nfe-emissao-service/
├── pom.xml
└── src/main/java/com/nfe/emissao/
    │
    ├── domain/                          ← CAMADA 1: Entidades puras
    │   ├── entity/
    │   │   ├── Nfe.java                 (classe Java pura, SEM @Entity)
    │   │   ├── NfeItem.java
    │   │   └── Empresa.java
    │   ├── valueobject/
    │   │   ├── ChaveAcesso.java         (value object imutável)
    │   │   ├── Cnpj.java               (validação embutida)
    │   │   └── ValorMonetario.java
    │   ├── exception/
    │   │   ├── NfeBusinessException.java
    │   │   └── NfeValidationException.java
    │   └── service/
    │       └── CalculoTotaisService.java (regra de domínio pura)
    │
    ├── application/                     ← CAMADA 2: Use cases + Ports
    │   ├── usecase/
    │   │   ├── EmitirNfeUseCase.java    (orquestra o fluxo de emissão)
    │   │   └── BuscarNfeUseCase.java
    │   ├── port/
    │   │   ├── out/
    │   │   │   ├── NfeRepository.java        (interface — persistência)
    │   │   │   ├── EmpresaRepository.java     (interface — persistência)
    │   │   │   ├── NfeEventPublisher.java     (interface — mensageria)
    │   │   │   └── NfeSequenceGenerator.java  (interface — gerar número)
    │   │   └── in/
    │   │       ├── EmitirNfeCommand.java      (input DTO do use case)
    │   │       └── NfeResult.java             (output DTO do use case)
    │   └── mapper/
    │       └── NfeApplicationMapper.java (domain ↔ DTOs de application)
    │
    ├── infrastructure/                  ← CAMADA 3: Adapters OUT
    │   ├── persistence/
    │   │   ├── entity/
    │   │   │   ├── NfeJpaEntity.java    (@Entity JPA — mapeia tabela)
    │   │   │   ├── NfeItemJpaEntity.java
    │   │   │   ├── EmpresaJpaEntity.java
    │   │   │   └── ImpostoJpaEntity.java
    │   │   ├── repository/
    │   │   │   ├── JpaNfeRepository.java         (implementa NfeRepository port)
    │   │   │   ├── JpaEmpresaRepository.java
    │   │   │   ├── SpringDataNfeRepository.java  (interface Spring Data JPA)
    │   │   │   └── SpringDataEmpresaRepository.java
    │   │   └── mapper/
    │   │       └── NfePersistenceMapper.java (domain ↔ JPA entity)
    │   ├── messaging/
    │   │   └── KafkaNfeEventPublisher.java  (implementa NfeEventPublisher port)
    │   ├── config/
    │   │   ├── KafkaConfig.java
    │   │   └── JpaConfig.java
    │   └── sequence/
    │       └── PostgresNfeSequenceGenerator.java (implementa NfeSequenceGenerator)
    │
    ├── adapter/                         ← CAMADA 4: Adapters IN
    │   └── in/
    │       ├── rest/
    │       │   ├── NfeRestController.java    (chama use cases)
    │       │   ├── dto/
    │       │   │   ├── EmitirNfeRequest.java (Record — JSON de entrada)
    │       │   │   └── NfeResponse.java      (Record — JSON de saída)
    │       │   ├── mapper/
    │       │   │   └── NfeRestMapper.java    (request/response ↔ commands/results)
    │       │   └── handler/
    │       │       └── GlobalExceptionHandler.java
    │       └── kafka/                        (consumers — se houver neste serviço)
    │
    └── NfeEmissaoApplication.java       (Spring Boot main)
```

---

## 4. Estrutura por Microsserviço

### nfe-commons (módulo compartilhado — NÃO é microsserviço)

Contém apenas artefatos que precisam ser compartilhados entre microsserviços. Com Clean Architecture, este módulo fica mais enxuto — apenas DTOs de eventos Kafka e constantes de integração:

```
nfe-commons/
└── src/main/java/com/nfe/commons/
    ├── event/                   ← Contratos de eventos Kafka (compartilhados entre serviços)
    │   ├── NfeEvent.java        (Record genérico com metadados)
    │   ├── NfeEmitidaPayload.java
    │   ├── NfeProcessadaPayload.java
    │   └── EventTypes.java
    ├── constant/                ← Constantes de integração
    │   ├── KafkaTopics.java
    │   └── StorageConstants.java
    └── dto/                     ← DTOs de API compartilhados (se houver comunicação síncrona)
        └── EmpresaDTO.java      (usado se consulta-service chama emissao-service)
```

**Importante**: enums de domínio (StatusNfe, RegimeTributario, etc.), exceções, validadores e entidades agora vivem DENTRO de cada microsserviço na camada `domain`. Se dois serviços precisam do mesmo enum, cada um tem sua própria cópia — isso é intencional na Clean Architecture. Acoplamento via commons deve ser minimizado.

### nfe-emissao-service (porta 8081)

```
domain/entity/        → Nfe, NfeItem, Empresa
domain/valueobject/   → ChaveAcesso, Cnpj, ValorMonetario
domain/exception/     → NfeBusinessException, NfeValidationException
domain/service/       → CalculoTotaisService
application/usecase/  → EmitirNfeUseCase, BuscarNfeUseCase
application/port/out/ → NfeRepository, EmpresaRepository, NfeEventPublisher, NfeSequenceGenerator
application/port/in/  → EmitirNfeCommand, NfeResult
infrastructure/       → JPA entities, Spring Data repos, Kafka publisher, Postgres sequence
adapter/in/rest/      → NfeRestController, DTOs request/response, GlobalExceptionHandler
```

### nfe-batch-processor (porta 8083)

```
domain/entity/        → Nfe, NfeItem, Imposto (cópia local com regras de cálculo)
domain/service/       → CalculoImpostosService (ICMS, PIS, COFINS, IPI — regra pura)
application/usecase/  → ProcessarImpostosUseCase, ReprocessarNfeUseCase
application/port/out/ → NfeRepository, ImpostoRepository, NfeEventPublisher
infrastructure/       → JPA entities, Spring Data repos, Kafka publisher
adapter/in/
    ├── batch/        → NfeBatchJobConfig, NfeItemReader, NfeItemProcessor, NfeItemWriter
    ├── kafka/        → NfeEmitidaKafkaListener (consome nfe.emitida, dispara batch)
    └── rest/         → BatchTriggerController (POST /api/batch/trigger)
```

### nfe-pdf-service (porta 8084)

```
domain/entity/        → Nfe, DanfeData (dados preparados para o template)
domain/service/       → DanfeDataAssembler (monta dados para o PDF — sem framework)
application/usecase/  → GerarDanfeUseCase, BuscarPdfUseCase
application/port/out/ → NfeRepository, PdfStorageGateway, PdfGenerator
infrastructure/
    ├── persistence/  → JPA entities + repos
    ├── storage/      → MinIoPdfStorageGateway (implementa PdfStorageGateway)
    ├── pdf/          → JasperPdfGenerator (implementa PdfGenerator, usa JasperReports)
    └── config/       → MinioConfig, JasperConfig
adapter/in/
    ├── kafka/        → NfeProcessadaKafkaListener (consome nfe.processada)
    └── rest/         → PdfRestController (GET /api/nfe/{id}/pdf)
```

### nfe-consulta-service (porta 8082)

```
domain/entity/        → Nfe, Empresa (somente leitura)
domain/valueobject/   → FiltroConsulta (período, CNPJ, status, faixa de valor)
application/usecase/  → ConsultarNfesUseCase, BuscarNfePorChaveUseCase
application/port/out/ → NfeQueryRepository (interface — read-only, com Specifications)
infrastructure/       → JPA entities, Spring Data JPA Specifications, read-only repos
adapter/in/rest/      → ConsultaRestController (GET /api/consulta/nfe com filtros)
```

### nfe-gateway (porta 8080)

```
(Sem Clean Architecture — é apenas configuração de roteamento)
config/               → GatewayRoutesConfig, RateLimitConfig, CorsConfig, SecurityConfig
```

---

## 5. Exemplo de Código — Clean Architecture em Ação

### domain/entity/Nfe.java (Java puro, sem anotações de framework)
```java
public class Nfe {
    private UUID id;
    private Long numero;
    private Integer serie;
    private ChaveAcesso chaveAcesso;
    private LocalDateTime dataEmissao;
    private NaturezaOperacao naturezaOperacao;
    private StatusNfe status;
    private Empresa emitente;
    private Empresa destinatario;
    private List<NfeItem> itens;
    private BigDecimal valorTotalProdutos;
    private BigDecimal valorTotalNfe;

    // Regra de domínio — vive na entidade
    public void calcularTotais() {
        this.valorTotalProdutos = itens.stream()
            .map(NfeItem::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotalNfe = this.valorTotalProdutos;
    }

    public boolean isOperacaoInterna() {
        return emitente.getUf().equals(destinatario.getUf());
    }

    public void validarTransicaoStatus(StatusNfe novoStatus) {
        if (!this.status.podeTransicionarPara(novoStatus)) {
            throw new NfeBusinessException("Transição inválida: " + status + " → " + novoStatus);
        }
    }
    // getters, setters...
}
```

### domain/valueobject/ChaveAcesso.java (imutável, auto-validante)
```java
public record ChaveAcesso(String valor) {
    public ChaveAcesso {
        if (valor == null || !valor.matches("\\d{44}")) {
            throw new NfeValidationException("Chave de acesso deve ter 44 dígitos");
        }
    }
    public static ChaveAcesso gerar(int codigoUf, LocalDateTime data, Cnpj cnpj, int serie, long numero) {
        // lógica de geração dos 44 dígitos...
        return new ChaveAcesso(chaveGerada);
    }
}
```

### domain/valueobject/Cnpj.java (auto-validante)
```java
public record Cnpj(String valor) {
    public Cnpj {
        String digits = valor.replaceAll("[^0-9]", "");
        if (!isValido(digits)) {
            throw new NfeValidationException("CNPJ inválido: " + valor);
        }
        valor = digits; // normaliza para só dígitos
    }
    private static boolean isValido(String digits) { /* algoritmo módulo 11 */ }
    public String formatado() { /* XX.XXX.XXX/XXXX-XX */ }
}
```

### application/port/out/NfeRepository.java (interface — port)
```java
public interface NfeRepository {
    Nfe salvar(Nfe nfe);
    Optional<Nfe> buscarPorId(UUID id);
    Optional<Nfe> buscarPorChaveAcesso(ChaveAcesso chave);
    Long proximoNumero();
}
```

### application/port/out/NfeEventPublisher.java (interface — port)
```java
public interface NfeEventPublisher {
    void publicarNfeEmitida(Nfe nfe);
    void publicarNfeProcessada(Nfe nfe);
}
```

### application/usecase/EmitirNfeUseCase.java (orquestra)
```java
@Service  // única anotação Spring permitida aqui
public class EmitirNfeUseCase {

    private final NfeRepository nfeRepository;
    private final EmpresaRepository empresaRepository;
    private final NfeEventPublisher eventPublisher;
    private final NfeSequenceGenerator sequenceGenerator;

    // constructor injection...

    public NfeResult executar(EmitirNfeCommand command) {
        // 1. Criar entidades de domínio a partir do command
        Empresa emitente = empresaRepository.buscarOuCriar(command.emitente());
        Empresa destinatario = empresaRepository.buscarOuCriar(command.destinatario());

        // 2. Montar NF-e com regras de domínio
        Nfe nfe = new Nfe();
        nfe.setNumero(sequenceGenerator.proximo());
        nfe.setSerie(command.serie());
        nfe.setChaveAcesso(ChaveAcesso.gerar(...));
        nfe.setEmitente(emitente);
        nfe.setDestinatario(destinatario);
        nfe.setItens(command.itens().stream().map(...).toList());
        nfe.calcularTotais(); // regra de domínio

        // 3. Persistir (via port)
        Nfe salva = nfeRepository.salvar(nfe);

        // 4. Publicar evento (via port)
        eventPublisher.publicarNfeEmitida(salva);

        return NfeApplicationMapper.toResult(salva);
    }
}
```

### infrastructure/persistence/repository/JpaNfeRepository.java (adapter out)
```java
@Repository
public class JpaNfeRepository implements NfeRepository {

    private final SpringDataNfeRepository springDataRepo;
    private final NfePersistenceMapper mapper;

    @Override
    public Nfe salvar(Nfe nfe) {
        NfeJpaEntity jpaEntity = mapper.toJpaEntity(nfe);
        NfeJpaEntity saved = springDataRepo.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Nfe> buscarPorId(UUID id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }
    // ...
}
```

### adapter/in/rest/NfeRestController.java (adapter in)
```java
@RestController
@RequestMapping("/api/nfe")
public class NfeRestController {

    private final EmitirNfeUseCase emitirUseCase;
    private final BuscarNfeUseCase buscarUseCase;
    private final NfeRestMapper restMapper;

    @PostMapping("/emitir")
    public ResponseEntity<NfeResponse> emitir(@Valid @RequestBody EmitirNfeRequest request) {
        EmitirNfeCommand command = restMapper.toCommand(request);
        NfeResult result = emitirUseCase.executar(command);
        NfeResponse response = restMapper.toResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

---

## 6. Mappers — 3 Níveis de Conversão

Com Clean Architecture, existem 3 tipos de mapper (cada um em sua camada):

| Mapper | Camada | Converte | Exemplo |
|--------|--------|----------|---------|
| NfeRestMapper | adapter.in | Request/Response ↔ Command/Result | EmitirNfeRequest → EmitirNfeCommand |
| NfeApplicationMapper | application | Command/Result ↔ Domain entities | EmitirNfeCommand → Nfe (domain) |
| NfePersistenceMapper | infrastructure | Domain entities ↔ JPA entities | Nfe (domain) → NfeJpaEntity |

Isso parece verboso, mas cada mapper tem responsabilidade única. O domínio nunca sabe que existe JSON ou JPA.

---

## 7. Decisões Técnicas

| Decisão | Escolha |
|---------|---------|
| Java | 17 |
| Spring Boot | 3.4.x |
| DTOs de API | Records |
| Entidades de domínio | Classes Java puras (sem @Entity) |
| Entidades JPA | Classes separadas na infrastructure (NfeJpaEntity) |
| Mapper | Manual (sem MapStruct) — 3 níveis |
| Banco | PostgreSQL 16 |
| Migrations | Flyway (V1 a V6) |
| Mensageria | Apache Kafka (Confluent 7.6.0) |
| Monorepo | Maven multi-module |
| Arquitetura | Clean Architecture (Hexagonal/Ports & Adapters) |

---

## 8. Modelo de Domínio

### Entidades e Relacionamentos
```
EMPRESA (1) ──emite──> (N) NFE
EMPRESA (1) ──recebe──> (N) NFE
NFE (1) ──contém──> (N) NFE_ITEM
NFE_ITEM (1) ──possui──> (1) IMPOSTO
NFE (1) ──gera──> (0..1) NFE_PDF
```

### Enums de Domínio (vivem em domain/ de cada microsserviço)

**StatusNfe**: PENDENTE → PROCESSADA → PDF_GERADO | CANCELADA | ERRO

**RegimeTributario**: SIMPLES_NACIONAL (3.95%), LUCRO_PRESUMIDO (11.33%), LUCRO_REAL (9.25%)

**NaturezaOperacao**: VENDA (5102/6102), DEVOLUCAO (5202/6202), TRANSFERENCIA (5152/6152), REMESSA (5901/6901), BONIFICACAO (5910/6910)

### Alíquotas de Impostos
- ICMS interno: 18%, interestadual: 7% (Sul/Sudeste→outros) ou 12%
- PIS: 1.65% (não cumulativo) ou 0.65% (cumulativo)
- COFINS: 7.60% (não cumulativo) ou 3.00% (cumulativo)
- IPI: 5.00% padrão

### Chave de Acesso (44 dígitos)
```
Pos  Tam  Campo
1    2    Código UF (IBGE)
3    4    Ano/mês (AAMM)
7    14   CNPJ emitente
21   2    Modelo (55 = NF-e)
23   3    Série
26   9    Número
35   1    Tipo emissão (1 = normal)
36   8    Código numérico aleatório
44   1    Dígito verificador (módulo 11)
```

---

## 9. Tabelas (Flyway V1-V6)

**empresa**: id (UUID PK), razao_social, nome_fantasia, cnpj (UK), inscricao_estadual, endereco, municipio, uf, cep, telefone, regime_tributario, created_at, updated_at

**nfe**: id (UUID PK), numero, serie, chave_acesso (UK 44 dígitos), data_emissao, data_processamento, natureza_operacao, valor_total_produtos, valor_total_impostos, valor_total_nfe, status (CHECK), emitente_id (FK), destinatario_id (FK), created_at, updated_at

**nfe_item**: id (UUID PK), nfe_id (FK CASCADE), numero_item, codigo_produto, descricao, ncm, cfop, unidade, quantidade, valor_unitario, valor_total, created_at

**imposto**: id (UUID PK), nfe_item_id (FK CASCADE UK), icms_base/aliquota/valor, pis_base/aliquota/valor, cofins_base/aliquota/valor, ipi_base/aliquota/valor, created_at

**nfe_pdf**: id (UUID PK), nfe_id (FK CASCADE UK), storage_key, bucket, file_size, gerado_em

**nfe_numero_seq**: sequence para numeração automática

---

## 10. Docker Compose — Serviços e Portas

| Serviço | Porta | Credenciais |
|---------|-------|-------------|
| PostgreSQL | 5432 | nfe_user / nfe_secret / nfe_db |
| Kafka | 9092 (host), 29092 (container) | — |
| Zookeeper | 2181 | — |
| Kafka UI | 8090 | — |
| MinIO API / Console | 9000 / 9001 | minioadmin / minioadmin123 |
| Redis | 6379 | — |
| Prometheus | 9090 | — |
| Grafana | 3000 | admin / admin123 |

Tópicos Kafka: `nfe.emitida` (3 part.), `nfe.processada` (3 part.), `nfe.emitida.dlq` (1 part.)

Bucket MinIO: `nfe-pdfs`

---

## 11. API Endpoints (nfe-emissao-service — porta 8081)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /api/nfe/emitir | Emite nova NF-e |
| GET | /api/nfe/{id} | Busca por UUID |
| GET | /api/nfe/chave/{chaveAcesso} | Busca por chave 44 dígitos |
| GET | /actuator/health | Health check |

---

## 12. Roadmap

| Fase | Nome | Status                               |
|------|------|--------------------------------------|
| 1 | Fundação + emissão-service | Precisa criar classes do nfe-emitter |
| 2 | Kafka consumers + testes integração | Próxima                              |
| 3 | Spring Batch (processamento impostos) | —                                    |
| 4 | PDF/DANFE (JasperReports + MinIO) | —                                    |
| 5 | Consulta + API Gateway | —                                    |
| 6 | Kubernetes + Istio | —                                    |
| 7 | Observabilidade + polish | —                                    |

---

## 13. Próximos Passos

1. **Refatorar nfe-emissao-service** para Clean Architecture (separar domain puro → ports → adapters)
2. **Refatorar nfe-commons** — mover enums e exceções para dentro de cada microsserviço, manter apenas eventos Kafka e constantes de integração
3. **Iniciar Fase 2** — Kafka consumer no batch-processor + testes com Testcontainers

Peça: "Vamos refatorar o nfe-emissao-service para Clean Architecture seguindo o padrão do documento de contexto"
