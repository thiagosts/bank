package br.com.bank.operation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import br.com.bank.operation.dto.TransferenciaRequestDTO;
import br.com.bank.operation.dto.ContasDto;
import br.com.bank.operation.dto.TransferenciaResponseDTO;
import br.com.bank.operation.service.TransferenciaService;

@ExtendWith(MockitoExtension.class)
class TransferenciaControllerTest {

    @Mock
    private TransferenciaService transferenciaService;
    @InjectMocks
    private TransferenciaController transferenciaController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferenciaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldProcessTransferSuccessfully() throws Exception {
        ContasDto contasDto = new ContasDto("idOrigem", "idDestino");
        TransferenciaRequestDTO transferenciaRequestDTO = new TransferenciaRequestDTO("idCliente", new BigDecimal("100.00"), contasDto);
        TransferenciaResponseDTO responseDto = new TransferenciaResponseDTO();
        given(transferenciaService.realizarTransferencia(any(TransferenciaRequestDTO.class))).willReturn(responseDto);
        mockMvc.perform(post("/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferenciaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
        verify(transferenciaService, times(1)).realizarTransferencia(any(TransferenciaRequestDTO.class));
    }
}