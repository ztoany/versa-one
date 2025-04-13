package io.github.ztoany.versa.infra.common.exception;

public class CustomHttpStatusException extends BaseException {
    private final int httpStatusCode;

    public CustomHttpStatusException(int httpStatusCode, String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        this.httpStatusCode = httpStatusCode;
    }

    public CustomHttpStatusException(int httpStatusCode, String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
