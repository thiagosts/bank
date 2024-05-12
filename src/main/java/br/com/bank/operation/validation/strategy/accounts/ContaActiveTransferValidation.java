package br.com.bank.operation.validation.strategy.accounts;

import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContaActiveTransferValidation implements AccountValidationStrategy {
    @Override
    public void accountValidate(ContaDto conta) {
        log.info("Verificação de conta ativa: ");
        if (conta != null && Boolean.FALSE.equals(conta.getAtivo())) {
            throw OperationException.contaInativa();
        }
    }
}
