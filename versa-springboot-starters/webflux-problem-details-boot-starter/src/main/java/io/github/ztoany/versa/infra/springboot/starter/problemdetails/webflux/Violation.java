package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webflux;

public record Violation(String field, String rejectedValue, String message) {
}
