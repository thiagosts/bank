package br.com.bank.operation.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacaoDto {
    BigDecimal valor;
    ContasDto conta;
}
