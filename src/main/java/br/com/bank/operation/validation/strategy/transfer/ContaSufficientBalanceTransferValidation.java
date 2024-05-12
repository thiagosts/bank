package br.com.bank.operation.validation.strategy.transfer;

import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.TransferValidationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ContaSufficientBalanceTransferValidation implements TransferValidationStrategy {
    @Override
    public void validate(ContaDto conta, BigDecimal valorTransferencia) {
        log.info("Validação de Saldo Suficiente");
        if (conta.getSaldo().compareTo(valorTransferencia) <= 0) {
            throw OperationException.saldoInsuficiente();
        }
    }
}
