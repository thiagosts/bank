package br.com.bank.operation.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCESSO_SOLICITACOES_MSG = "Excesso de solicitações ao serviço externo.";
    private static final String ERRO_SERVICO_EXTERNO_MSG = "Erro ao comunicar com serviço externo: ";
    private static final String ERRO_INTERNO_MSG = "Ocorreu um erro interno no servidor.";

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<Object> handleOperationException(OperationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException ex) {
        if (ex.status() == 429) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(EXCESSO_SOLICITACOES_MSG);
        }
        return ResponseEntity.status(HttpStatus.valueOf(ex.status()))
                .body(ERRO_SERVICO_EXTERNO_MSG + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(ERRO_INTERNO_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
