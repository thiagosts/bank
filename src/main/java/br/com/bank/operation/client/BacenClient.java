package br.com.bank.operation.client;

import br.com.bank.operation.dto.NotificacaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "bacenService", url = "${bacen.client.url}")
public interface BacenClient {
    @PostMapping
    void notificarBacen(NotificacaoDto notificacao);
}
