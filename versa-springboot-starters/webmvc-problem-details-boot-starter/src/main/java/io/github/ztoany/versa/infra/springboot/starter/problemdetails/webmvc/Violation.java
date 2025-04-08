package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webmvc;

public record Violation(String field, String rejectedValue, String message) {
}
