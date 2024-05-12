package br.com.bank.operation.validation.strategy.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import java.math.BigDecimal;

class ContaDestActiveTransferValidationTest {
    private AccountValidationStrategy accountValidationStrategy;

    @BeforeEach
    void setUp() {
        accountValidationStrategy = new ContaDestActiveTransferValidation();
    }

    @Test
    void shouldNotThrowExceptionWhenAccountDestIsActive() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(1500));
        assertDoesNotThrow(() -> accountValidationStrategy.accountValidate(conta));
    }

    @Test
    void shouldThrowExceptionWhenAccountDestIsInactive() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), false, BigDecimal.valueOf(1500));
        Exception exception = assertThrows(OperationException.class, () -> accountValidationStrategy.accountValidate(conta));
        assertEquals("Conta de destino inativa.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenAccountDestIsNull() {
        assertDoesNotThrow(() -> accountValidationStrategy.accountValidate(null));
    }
}