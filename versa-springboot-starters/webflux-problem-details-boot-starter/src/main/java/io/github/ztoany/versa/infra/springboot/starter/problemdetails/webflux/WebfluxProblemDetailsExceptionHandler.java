package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webflux;

import io.github.ztoany.versa.infra.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class WebfluxProblemDetailsExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger SLF4J_LOGGER = LoggerFactory.getLogger(WebfluxProblemDetailsExceptionHandler.class);

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(
            WebExchangeBindException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            ServerWebExchange exchange) {
        ProblemDetailsBuilder.violations(ex.getBody(), ex.getBindingResult());
        return handleExceptionInternal(ex, null, headers, status, exchange);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleEntityNotFoundException(
            EntityNotFoundException ex,
            ServerWebExchange exchange) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, exchange);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    protected Mono<ResponseEntity<Object>> handleAuthenticationFailedException(
            AuthenticationFailedException ex,
            ServerWebExchange exchange) {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, exchange);
    }

    @ExceptionHandler(BusinessException.class)
    protected Mono<ResponseEntity<Object>> handleBusinessException(
            BusinessException ex,
            ServerWebExchange exchange) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, exchange);
    }

    @ExceptionHandler(CustomHttpStatusException.class)
    protected Mono<ResponseEntity<Object>> handleCustomHttpStatusException(
            CustomHttpStatusException ex,
            ServerWebExchange exchange) {
        HttpStatusCode status = HttpStatus.valueOf(ex.getHttpStatusCode());
        var problemDetail = buildProblemDetail(status, ex);
        return handleExceptionInternal(ex, problemDetail, null, status, exchange);
    }

    @ExceptionHandler(Exception.class)
    protected Mono<ResponseEntity<Object>> handleUnhandledException(
            Exception ex,
            ServerWebExchange exchange) {
        SLF4J_LOGGER.error(ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemDetail = ProblemDetail.forStatusAndDetail(status, status.getReasonPhrase());
        ProblemDetailsBuilder.timestamp(problemDetail);
        return super.handleExceptionInternal(ex, problemDetail, null, status, exchange);
    }

    protected ProblemDetail buildProblemDetail(HttpStatusCode status, BaseException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getErrorMessage());
        ProblemDetailsBuilder.code(problemDetail, ex.getErrorCode());
        ProblemDetailsBuilder.timestamp(problemDetail);
        return problemDetail;
    }

    protected ProblemDetail createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, String detailMessageCode, Object[] detailMessageArguments, ServerWebExchange exchange) {
        var problemDetail = super.createProblemDetail(ex, status, defaultDetail, detailMessageCode, detailMessageArguments, exchange);
        ProblemDetailsBuilder.timestamp(problemDetail);
        return problemDetail;
    }

    protected Mono<ResponseEntity<Object>> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            ServerWebExchange exchange) {
        SLF4J_LOGGER.warn(ex.getMessage());

        if(ex instanceof ErrorResponse errorResponse) {
            ProblemDetailsBuilder.timestamp(errorResponse.getBody());
        }
        return super.handleExceptionInternal(ex, body, headers, status, exchange);
    }
}
