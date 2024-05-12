package br.com.bank.operation.client;

import static org.mockito.Mockito.*;
import br.com.bank.operation.dto.ContaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ContaClientTest {
    @Mock
    private ContaClient contaClient;

    @Test
    void testGetContaById() {
        String contaId = "456";
        ContaDto expectedConta = new ContaDto();
        expectedConta.setId(contaId);
        expectedConta.setSaldo(BigDecimal.valueOf(500.00));
        when(contaClient.getContaById(contaId)).thenReturn(expectedConta);
        ContaDto result = contaClient.getContaById(contaId);
        verify(contaClient).getContaById(contaId);
        assert result.equals(expectedConta);
    }
}