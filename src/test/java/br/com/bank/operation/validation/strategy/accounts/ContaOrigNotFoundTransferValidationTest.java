package br.com.bank.operation.validation.strategy.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import br.com.bank.operation.client.ContaClient;
import br.com.bank.operation.dto.ContaDto;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import java.math.BigDecimal;

class ContaOrigNotFoundTransferValidationTest {

    @Mock
    private ContaClient contaClient;

    private AccountValidationStrategy accountValidationStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountValidationStrategy = new ContaOrigNotFoundTransferValidation();
    }

    @Test
    void shouldThrowExceptionWhenAccountOrigIsNull() {
        Exception exception = assertThrows(OperationException.class, () -> accountValidationStrategy.accountValidate(null));
        assertEquals("Conta de origem não encontrada.", exception.getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenAccountOrigIsNotNull() {
        ContaDto conta = new ContaDto("contaId", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(1500));
        assertDoesNotThrow(() -> accountValidationStrategy.accountValidate(conta));
    }
}