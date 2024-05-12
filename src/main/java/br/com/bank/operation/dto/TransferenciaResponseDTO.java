package br.com.bank.operation.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponseDTO {
    private UUID idTransferencia;
}