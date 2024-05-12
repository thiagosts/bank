package br.com.bank.operation.validation.strategy.accounts;

import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContaDestNotFoundTransferValidation implements AccountValidationStrategy {

    @Override
    public void accountValidate(ContaDto conta) {
        log.info("Verifica se existe a conta de destino: {}", conta != null);
        if (conta == null) {
            throw OperationException.contaDestinoNotFound();
        }
    }
}
