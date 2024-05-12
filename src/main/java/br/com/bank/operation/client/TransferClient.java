package br.com.bank.operation.client;

import br.com.bank.operation.dto.TransferenciaRequestDTO;
import br.com.bank.operation.dto.TransferenciaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transferClient", url = "${transfer.client.url}")
public interface TransferClient {
    @PostMapping(value = "/transferencia", consumes = "application/json", produces = "application/json")
    TransferenciaResponseDTO realizarTransferencia(@RequestBody TransferenciaRequestDTO transferencia);
}
