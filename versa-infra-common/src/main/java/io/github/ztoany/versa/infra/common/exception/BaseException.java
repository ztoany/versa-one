package io.github.ztoany.versa.infra.common.exception;

public class BaseException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public BaseException(String errorCode, String errorMessage) {
        super(buildMessage(errorCode, errorMessage));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseException(String errorCode, String errorMessage, Throwable cause) {
        super(buildMessage(errorCode, errorMessage), cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private static String buildMessage(String errorCode, String errorMessage) {
        return String.format("%s - %s", errorCode, errorMessage);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
