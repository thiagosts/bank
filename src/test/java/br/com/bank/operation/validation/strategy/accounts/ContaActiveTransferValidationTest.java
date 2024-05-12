package br.com.bank.operation.validation.strategy.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import java.math.BigDecimal;

class ContaActiveTransferValidationTest {

    private AccountValidationStrategy accountValidationStrategy;

    @BeforeEach
    void setUp() {
        accountValidationStrategy = new ContaActiveTransferValidation();
    }

    @Test
    void shouldNotThrowExceptionWhenAccountIsActive() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(1500));
        assertDoesNotThrow(() -> accountValidationStrategy.accountValidate(conta));
    }

    @Test
    void shouldThrowExceptionWhenAccountIsInactive() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), false, BigDecimal.valueOf(1500));
        Exception exception = assertThrows(OperationException.class, () -> accountValidationStrategy.accountValidate(conta));
        assertEquals("Conta de origem inativa.", exception.getMessage());
    }
}