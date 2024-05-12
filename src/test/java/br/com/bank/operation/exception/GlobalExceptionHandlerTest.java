package br.com.bank.operation.exception;

import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @Test
    void whenOperationExceptionThrown_thenBadRequest() throws Exception {
        mockMvc.perform(get("/dummy")
                        .requestAttr("javax.servlet.error.exception", OperationException.contaOrigemNotFound()))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Ocorreu um erro interno no servidor."));
    }
    @Test
    void whenGenericFeignExceptionThrown_thenServiceError() throws Exception {
        Response response = Response.builder()
                .status(500)
                .reason("Server error")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, null, null))
                .build();
        FeignException feignException = FeignException.errorStatus("Test", response);

        mockMvc.perform(get("/dummy")
                        .requestAttr("javax.servlet.error.exception", feignException))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ocorreu um erro interno no servidor."));
    }
    @Test
    void whenGeneralExceptionThrown_thenInternalServerError() throws Exception {
        mockMvc.perform(get("/dummy")
                        .requestAttr("javax.servlet.error.exception", new Exception("Generic error")))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ocorreu um erro interno no servidor."));
    }
}