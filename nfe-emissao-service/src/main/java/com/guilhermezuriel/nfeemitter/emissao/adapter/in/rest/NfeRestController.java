package com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest;

import com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto.EmitirNfeRequest;
import com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto.NfeResponse;
import com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.mapper.NfeRestMapper;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.EmitirNfeCommand;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.NfeResult;
import com.guilhermezuriel.nfeemitter.emissao.application.usecase.BuscarNfeUseCase;
import com.guilhermezuriel.nfeemitter.emissao.application.usecase.EmitirNfeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/nfe")
public class NfeRestController {

    private final EmitirNfeUseCase emitirUseCase;
    private final BuscarNfeUseCase buscarUseCase;
    private final NfeRestMapper restMapper;

    public NfeRestController(EmitirNfeUseCase emitirUseCase,
                             BuscarNfeUseCase buscarUseCase,
                             NfeRestMapper restMapper) {
        this.emitirUseCase = emitirUseCase;
        this.buscarUseCase = buscarUseCase;
        this.restMapper = restMapper;
    }

    @PostMapping("/emitir")
    public ResponseEntity<NfeResponse> emitir(@Valid @RequestBody EmitirNfeRequest request) {
        EmitirNfeCommand command = restMapper.toCommand(request);
        NfeResult result = emitirUseCase.executar(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(restMapper.toResponse(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NfeResponse> buscarPorId(@PathVariable UUID id) {
        NfeResult result = buscarUseCase.buscarPorId(id);
        return ResponseEntity.ok(restMapper.toResponse(result));
    }

    @GetMapping("/chave/{chaveAcesso}")
    public ResponseEntity<NfeResponse> buscarPorChave(@PathVariable String chaveAcesso) {
        NfeResult result = buscarUseCase.buscarPorChaveAcesso(chaveAcesso);
        return ResponseEntity.ok(restMapper.toResponse(result));
    }
}
