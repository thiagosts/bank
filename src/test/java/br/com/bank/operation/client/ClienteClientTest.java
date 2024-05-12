package br.com.bank.operation.client;

import static org.mockito.Mockito.*;
import br.com.bank.operation.dto.ClienteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteClientTest {
    @Mock
    private ClienteClient clienteClient;

    @Test
    void testGetClienteById() {
        String clienteId = "123";
        ClienteDto expectedCliente = new ClienteDto();
        expectedCliente.setId(clienteId);
        expectedCliente.setNome("João Silva");
        expectedCliente.setTelefone("11999820000");
        when(clienteClient.getClienteById(clienteId)).thenReturn(expectedCliente);
        ClienteDto result = clienteClient.getClienteById(clienteId);
        verify(clienteClient).getClienteById(clienteId);
        assert result.equals(expectedCliente);
    }
}