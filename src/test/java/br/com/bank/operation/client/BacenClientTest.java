package br.com.bank.operation.client;

import br.com.bank.operation.dto.NotificacaoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BacenClientTest {

    @Mock
    private BacenClient bacenClient;

    @Test
    void testNotificarBacen() {
        NotificacaoDto notificacaoDto = new NotificacaoDto();
        doNothing().when(bacenClient).notificarBacen(notificacaoDto);
        bacenClient.notificarBacen(notificacaoDto);
        verify(bacenClient).notificarBacen(notificacaoDto);
    }
}