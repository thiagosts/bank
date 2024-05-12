package br.com.bank.operation.client;

import br.com.bank.operation.dto.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clienteService", url = "${cliente.client.url}")
public interface ClienteClient {
    @GetMapping("/{id}")
    ClienteDto getClienteById(@PathVariable("id") String id);
}
