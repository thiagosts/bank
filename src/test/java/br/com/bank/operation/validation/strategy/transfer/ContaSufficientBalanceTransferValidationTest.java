package br.com.bank.operation.validation.strategy.transfer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.TransferValidationStrategy;

class ContaSufficientBalanceTransferValidationTest {

    private TransferValidationStrategy transferValidationStrategy;

    @BeforeEach
    void setUp() {
        transferValidationStrategy = new ContaSufficientBalanceTransferValidation();
    }

    @Test
    void shouldNotThrowExceptionWhenBalanceIsSufficient() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(2000), true, BigDecimal.valueOf(1500));
        BigDecimal valorTransferencia = BigDecimal.valueOf(1500);
        assertDoesNotThrow(() -> transferValidationStrategy.validate(conta, valorTransferencia));
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(1500));
        BigDecimal valorTransferencia = BigDecimal.valueOf(1200);
        Exception exception = assertThrows(OperationException.class, () -> transferValidationStrategy.validate(conta, valorTransferencia));
        assertEquals("Saldo insuficiente para realizar a transferência.", exception.getMessage());
    }
}