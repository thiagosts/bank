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
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferenciaServiceTest {

    @Mock
    private ClienteClient clienteClient;
    @Mock
    private ContaClient contaClient;
    @Mock
    private BacenClient bacenClient;
    @Mock
    private TransferClient transferClient;
    @Mock
    private AccountValidationStrategy accountValidationStrategy;
    @Mock
    private TransferValidationStrategy transferValidationStrategy;

    @InjectMocks
    private TransferenciaService service;

    private TransferenciaRequestDTO transferenciaRequestDTO;
    private ClienteDto clienteDto;
    private ContaDto contaOrigem;
    private ContaDto contaDestino;
    private TransferenciaResponseDTO responseDto;

    @BeforeEach
    void setUp() {
        transferenciaRequestDTO = new TransferenciaRequestDTO(UUID.randomUUID().toString(), new BigDecimal("1000.00"), new ContasDto("origemId", "destinoId"));
        clienteDto = new ClienteDto("clienteId", "Cliente Teste", "1234567890", "Fisica");
        contaOrigem = new ContaDto("origemId", BigDecimal.valueOf(5000), true, BigDecimal.valueOf(2000));
        contaDestino = new ContaDto("destinoId", BigDecimal.valueOf(5000), true, BigDecimal.valueOf(2000));
        responseDto = new TransferenciaResponseDTO();
        List<AccountValidationStrategy> accountValidationStrategies = Arrays.asList(accountValidationStrategy);
        List<TransferValidationStrategy> transferValidationStrategies = Arrays.asList(transferValidationStrategy);
        service = new TransferenciaService(clienteClient, contaClient, bacenClient, transferClient, accountValidationStrategies, transferValidationStrategies);
        lenient().when(clienteClient.getClienteById(anyString())).thenReturn(clienteDto);
        lenient().when(contaClient.getContaById("origemId")).thenReturn(contaOrigem);
        lenient().when(contaClient.getContaById("destinoId")).thenReturn(contaDestino);
        lenient().when(transferClient.realizarTransferencia(transferenciaRequestDTO)).thenReturn(responseDto);
        lenient().doNothing().when(accountValidationStrategy).accountValidate(any(ContaDto.class));
        lenient().doNothing().when(transferValidationStrategy).validate(any(ContaDto.class), any(BigDecimal.class));
    }

    @Test
    void shouldCompleteTransferSuccessfully() {
        TransferenciaResponseDTO result = service.realizarTransferencia(transferenciaRequestDTO);
        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(transferClient).realizarTransferencia(transferenciaRequestDTO);
        verify(bacenClient).notificarBacen(any(NotificacaoDto.class));
    }

    @Test
    void shouldFailIfClienteNotFound() {
        when(clienteClient.getClienteById(anyString())).thenReturn(null);
        assertThrows(OperationException.class, () -> service.realizarTransferencia(transferenciaRequestDTO));
    }

    @Test
    void shouldFailIfContaOrigemNotFound() {
        when(contaClient.getContaById("origemId")).thenReturn(null);
        assertThrows(OperationException.class, () -> service.realizarTransferencia(transferenciaRequestDTO));
    }

    @Test
    void shouldFailIfContaOrigemInactive() {
        contaOrigem = new ContaDto("origemId", BigDecimal.valueOf(5000), false, BigDecimal.valueOf(2000));
        when(contaClient.getContaById("origemId")).thenReturn(contaOrigem);

        doAnswer(invocation -> {
            ContaDto conta = invocation.getArgument(0);
            if (!conta.getAtivo()) {
                throw OperationException.contaInativa();
            }
            return null;
        }).when(accountValidationStrategy).accountValidate(any(ContaDto.class));

        assertThrows(OperationException.class, () -> service.realizarTransferencia(transferenciaRequestDTO));
    }

    @Test
    void shouldFailIfInsufficientBalance() {
        contaOrigem = new ContaDto("origemId", BigDecimal.valueOf(500), true, BigDecimal.valueOf(2000));
        when(contaClient.getContaById("origemId")).thenReturn(contaOrigem);
        doAnswer(invocation -> {
            ContaDto conta = invocation.getArgument(0);
            BigDecimal valor = invocation.getArgument(1);
            if (conta.getSaldo().compareTo(valor) < 0) {
                throw OperationException.saldoInsuficiente();
            }
            return null;
        }).when(transferValidationStrategy).validate(any(ContaDto.class), any(BigDecimal.class));
        assertThrows(OperationException.class, () -> service.realizarTransferencia(transferenciaRequestDTO));
    }

    @Test
    void shouldHandleRateLimitExceededDuringBacenNotification() {
        doThrow(new FeignException.FeignClientException(429, "Rate limit exceeded",
                Request.create(Request.HttpMethod.POST, "http://example.com", Collections.emptyMap(), null, Charset.defaultCharset()), null, null))
                .when(bacenClient).notificarBacen(any(NotificacaoDto.class));
        assertThrows(OperationException.class, () -> service.realizarTransferencia(transferenciaRequestDTO));
    }
}