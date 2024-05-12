package br.com.bank.operation.validation.strategy.accounts;

import br.com.bank.operation.client.ContaClient;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContaOrigNotFoundTransferValidation implements AccountValidationStrategy {

    @Override
    public void accountValidate(ContaDto conta) {
        log.info("Verifica se a conta de origem existe: {}", conta != null);
        if (conta == null) {
            throw OperationException.contaOrigemNotFound();
        }
    }

}
