package br.com.bank.operation.validation.strategy;

import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;

import java.math.BigDecimal;

public interface TransferValidationStrategy {
    void validate(ContaDto conta, BigDecimal valorTransferencia) throws OperationException;
}
