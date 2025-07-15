package io.github.ztoany.versa.infra.common.exception;

public class AuthenticationFailedException extends BusinessException {
    public AuthenticationFailedException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public AuthenticationFailedException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
