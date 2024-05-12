package br.com.bank.operation.validation.strategy;

import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;

public interface AccountValidationStrategy {
    void accountValidate(ContaDto conta) throws OperationException;
}
