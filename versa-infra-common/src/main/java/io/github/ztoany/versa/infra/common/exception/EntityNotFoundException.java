package io.github.ztoany.versa.infra.common.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public EntityNotFoundException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
