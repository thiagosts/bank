package br.com.bank.operation.exception;

public class OperationException extends RuntimeException {

    public static final String CLIENTE_NOT_FOUND_MSG = "Cliente não encontrado.";
    public static final String CONTA_NOT_FOUND_MSG = "Conta de origem não encontrada.";
    public static final String CONTA_DEST_NOT_FOUND_MSG = "Conta de destino não encontrada.";
    public static final String CONTA_INATIVA_MSG = "Conta de origem inativa.";
    public static final String CONTA_DEST_INATIVA_MSG = "Conta de destino inativa.";
    public static final String SALDO_INSUFICIENTE_MSG = "Saldo insuficiente para realizar a transferência.";
    public static final String LIMITE_DIARIO_EXCEDIDO_MSG = "O valor da transferência excede o limite diário.";
    public static final String RATE_LIMIT_EXCEEDED_MSG = "Limite de taxa de notificação do BACEN excedido.";
    public static final String BACEN_NOTIFICACAO_FAILED_MSG = "Falha ao notificar o BACEN.";

    private OperationException(String message) {
        super(message);
    }

    public static OperationException clienteNotFound() {
        return new OperationException(CLIENTE_NOT_FOUND_MSG);
    }

    public static OperationException contaOrigemNotFound() {
        return new OperationException(CONTA_NOT_FOUND_MSG);
    }

    public static OperationException contaDestinoNotFound() {
        return new OperationException(CONTA_DEST_NOT_FOUND_MSG);
    }

    public static OperationException contaInativa() {
        return new OperationException(CONTA_INATIVA_MSG);
    }

    public static OperationException contaDestInativa() {
        return new OperationException(CONTA_DEST_INATIVA_MSG);
    }

    public static OperationException saldoInsuficiente() {
        return new OperationException(SALDO_INSUFICIENTE_MSG);
    }

    public static OperationException limiteDiarioExcedido() {
        return new OperationException(LIMITE_DIARIO_EXCEDIDO_MSG);
    }

    public static OperationException rateLimitExceeded() {
        return new OperationException(RATE_LIMIT_EXCEEDED_MSG);
    }

    public static OperationException bacenNotificacaoFailed() {
        return new OperationException(BACEN_NOTIFICACAO_FAILED_MSG);
    }
}
