package br.com.bank.operation.client;

import br.com.bank.operation.dto.ContaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "contaService", url = "${conta.client.url}")
public interface ContaClient {
    @GetMapping("/{id}")
    ContaDto getContaById(@PathVariable("id") String id);
}
