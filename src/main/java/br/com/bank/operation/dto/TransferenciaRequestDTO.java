package br.com.bank.operation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferenciaRequestDTO {
    String idCliente;
    BigDecimal valor;
    ContasDto conta;
}



