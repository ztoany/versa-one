package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webflux;

import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProblemDetailsBuilder {
    private static final String TIMESTAMP_FIELD = "timestamp";
    private static final String CODE_FIELD = "code";
    private static final String VIOLATIONS_FIELD = "violations";

    public static void detail(ProblemDetail problemDetail, String detail) {
        problemDetail.setDetail(detail);
    }

    public static void code(ProblemDetail problemDetail, String code) {
        problemDetail.setProperty(CODE_FIELD, code);
    }

    public static void timestamp(ProblemDetail problemDetail) {
        problemDetail.setProperty(TIMESTAMP_FIELD, Date.from(Instant.now()));
    }

    public static void violations(ProblemDetail problemDetail, BindingResult bindingResult) {
        List<Violation> violations = new LinkedList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String field = fieldError.getField();
            String rejectedValue = fieldError.getRejectedValue() == null ?
                    null :
                    fieldError.getRejectedValue().toString();
            String message = fieldError.getDefaultMessage();
            var violation = new Violation(field, rejectedValue, message);
            violations.add(violation);
        }

        problemDetail.setProperty(VIOLATIONS_FIELD, violations);
    }
}
