package br.com.bank.operation.validation.strategy.transfer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.TransferValidationStrategy;

class ContaDailyLimitTransferValidationTest {

    private TransferValidationStrategy transferValidationStrategy;

    @BeforeEach
    void setUp() {
        transferValidationStrategy = new ContaDailyLimitTransferValidation();
    }

    @Test
    void shouldNotThrowExceptionWhenTransferIsWithinLimit() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(10000), true, BigDecimal.valueOf(1000));
        BigDecimal valorTransferencia = BigDecimal.valueOf(800);
        assertDoesNotThrow(() -> transferValidationStrategy.validate(conta, valorTransferencia));
    }

    @Test
    void shouldThrowExceptionWhenTransferExceedsDailyLimit() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(10000), true, BigDecimal.valueOf(500));
        BigDecimal valorTransferencia = BigDecimal.valueOf(700);
        Exception exception = assertThrows(OperationException.class, () -> transferValidationStrategy.validate(conta, valorTransferencia));
        assertTrue(exception.getMessage().contains("O valor da transferência excede o limite diário."));
    }
}