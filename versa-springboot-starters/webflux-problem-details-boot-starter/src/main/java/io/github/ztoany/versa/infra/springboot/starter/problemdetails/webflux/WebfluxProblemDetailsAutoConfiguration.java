package io.github.ztoany.versa.infra.springboot.starter.problemdetails.webflux;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@AutoConfiguration(after = {
        ReactiveWebServerFactoryAutoConfiguration.class,
        CodecsAutoConfiguration.class,
        ReactiveMultipartAutoConfiguration.class,
        ValidationAutoConfiguration.class,
        WebSessionIdResolverAutoConfiguration.class },
        before = {WebFluxAutoConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
public class WebfluxProblemDetailsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResponseEntityExceptionHandler.class)
    public WebfluxProblemDetailsExceptionHandler webfluxProblemDetailsExceptionHandler() {
        return new WebfluxProblemDetailsExceptionHandler();
    }
}
