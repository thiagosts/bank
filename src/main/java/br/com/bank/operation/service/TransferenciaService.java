package br.com.bank.operation.service;

import br.com.bank.operation.client.BacenClient;
import br.com.bank.operation.client.ClienteClient;
import br.com.bank.operation.client.ContaClient;
import br.com.bank.operation.client.TransferClient;
import br.com.bank.operation.dto.*;
import br.com.bank.operation.exception.OperationException;
import br.com.bank.operation.validation.strategy.AccountValidationStrategy;
import br.com.bank.operation.validation.strategy.TransferValidationStrategy;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {

    private final ClienteClient clienteClient;
    private final ContaClient contaClient;
    private final BacenClient bacenClient;
    private final TransferClient transferClient;
    private final List<AccountValidationStrategy> accountValidationStrategies;
    private final List<TransferValidationStrategy> transferValidationStrategies;

    public TransferenciaResponseDTO realizarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO) {

        ClienteDto clienteDto = validateCliente(transferenciaRequestDTO.getIdCliente());
        log.info("Client Id Found: {}", clienteDto.getId());

        ContaDto contaOrigem = validateContaOrigem(transferenciaRequestDTO.getConta().getIdOrigem());
        log.info("Conta de Origem Válida para realizar transferência: {}", contaOrigem.getId());

        ContaDto contaDestino = validateContaDestino(transferenciaRequestDTO.getConta().getIdDestino());
        log.info("Conta de Destino Válida para receber a transferência: {}", contaDestino.getId());

        validateTransfer(contaOrigem, transferenciaRequestDTO.getValor());
        log.info("Validação para Transferência com sucesso");

        TransferenciaResponseDTO response = transferClient.realizarTransferencia(transferenciaRequestDTO);
        log.info("Transferência realizada com sucesso com id: {}", response);

        notificarBacen(transferenciaRequestDTO);
        log.info("Notificação para bacen enviada");

        return response;
    }
    private ClienteDto validateCliente(String clienteId) {
        return Optional.ofNullable(clienteClient.getClienteById(clienteId))
                .orElseThrow(OperationException::clienteNotFound);
    }
    private ContaDto validateContaOrigem(String contaId) {
        ContaDto conta = contaClient.getContaById(contaId);
        return Optional.ofNullable(validateConta(conta))
                .orElseThrow(OperationException::contaOrigemNotFound);
    }
    private ContaDto validateContaDestino(String contaId) {
        ContaDto conta = contaClient.getContaById(contaId);
        return Optional.ofNullable(validateConta(conta))
                .orElseThrow(OperationException::contaDestinoNotFound);
    }
    private ContaDto validateConta(ContaDto conta) {
        if (conta == null) {
            return null;
        }
        accountValidationStrategies.forEach(strategy -> strategy.accountValidate(conta));
        return conta;
    }
    private void validateTransfer(ContaDto conta, BigDecimal valorTransferencia) {
        transferValidationStrategies.forEach(strategy -> strategy.validate(conta, valorTransferencia));
    }
    private void notificarBacen(TransferenciaRequestDTO transferenciaRequestDTO) {
        try {
            bacenClient.notificarBacen(new NotificacaoDto(transferenciaRequestDTO.getValor(), transferenciaRequestDTO.getConta()));
        } catch (FeignException e) {
            if (e.status() == 429) {
                throw OperationException.rateLimitExceeded();
            }
            throw OperationException.bacenNotificacaoFailed();
        }
    }
}
