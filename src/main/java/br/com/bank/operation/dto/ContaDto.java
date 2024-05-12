package br.com.bank.operation.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaDto{
    String id;
    BigDecimal saldo;
    Boolean ativo;
    BigDecimal limiteDiario;

}
