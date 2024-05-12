package br.com.bank.operation.client;

import static org.mockito.Mockito.*;
import br.com.bank.operation.dto.TransferenciaRequestDTO;
import br.com.bank.operation.dto.TransferenciaResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransferClientTest {
    @Mock
    private TransferClient transferClient;

    @Test
    void testRealizarTransferencia() {
        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO();
        TransferenciaResponseDTO expectedResponse = new TransferenciaResponseDTO();
        when(transferClient.realizarTransferencia(requestDTO)).thenReturn(expectedResponse);
        TransferenciaResponseDTO actualResponse = transferClient.realizarTransferencia(requestDTO);
        verify(transferClient).realizarTransferencia(requestDTO);
        assert actualResponse.equals(expectedResponse);
    }
}