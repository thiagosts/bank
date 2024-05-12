package br.com.bank.operation.controller;

import br.com.bank.operation.dto.TransferenciaRequestDTO;
import br.com.bank.operation.dto.TransferenciaResponseDTO;
import br.com.bank.operation.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaResponseDTO> realizarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        TransferenciaResponseDTO response = transferenciaService.realizarTransferencia(transferenciaRequestDTO);
        return ResponseEntity.ok(response);
    }

}
