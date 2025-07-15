package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webmvc;

import io.github.ztoany.versa.infra.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebMvcProblemDetailsExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger SLF4J_LOGGER = LoggerFactory.getLogger(WebMvcProblemDetailsExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ProblemDetailsBuilder.violations(ex.getBody(), ex.getBindingResult());
        return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, request);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    protected ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, request);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, request);
    }

    @ExceptionHandler(CustomHttpStatusException.class)
    protected ResponseEntity<Object> handleCustomHttpStatusException(CustomHttpStatusException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.valueOf(ex.getHttpStatusCode());
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnhandledException(Exception ex, WebRequest request) {
        SLF4J_LOGGER.error(ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemDetail = ProblemDetail.forStatusAndDetail(status, status.getReasonPhrase());
        ProblemDetailsBuilder.timestamp(problemDetail);
        return super.handleExceptionInternal(ex, problemDetail, null, status, request);
    }

    protected ProblemDetail buildProblemDetail(HttpStatusCode status, BaseException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getErrorMessage());
        ProblemDetailsBuilder.code(problemDetail, ex.getErrorCode());
        ProblemDetailsBuilder.timestamp(problemDetail);
        return problemDetail;
    }

    protected ProblemDetail createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, String detailMessageCode, Object[] detailMessageArguments, WebRequest request) {
        var problemDetail = super.createProblemDetail(ex, status, defaultDetail, detailMessageCode, detailMessageArguments, request);
        ProblemDetailsBuilder.timestamp(problemDetail);
        return problemDetail;
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        var logLevel = getLogLevel(ex);
        if(logLevel == LogLevel.WARN) {
            SLF4J_LOGGER.warn(ex.getMessage());
        }

        if(ex instanceof ErrorResponse errorResponse) {
            ProblemDetailsBuilder.timestamp(errorResponse.getBody());
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private LogLevel getLogLevel(Exception ex) {
        if(ex instanceof HttpRequestMethodNotSupportedException) {
            return LogLevel.IGNORE;
        } else {
            return LogLevel.WARN;
        }
    }
}
