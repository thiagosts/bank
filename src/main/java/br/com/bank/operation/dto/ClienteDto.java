package br.com.bank.operation.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDto {
    String id;
    String nome;
    String telefone;
    String tipoPessoa;
}
