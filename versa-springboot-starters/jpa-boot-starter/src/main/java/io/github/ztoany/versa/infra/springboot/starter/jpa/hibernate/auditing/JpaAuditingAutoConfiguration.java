package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.auditing;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

@AutoConfiguration
public class JpaAuditingAutoConfiguration {
    @Bean
    public AuditorAware springSecurityOidcStringUserAuditorAware() {
        return new SpringSecurityOidcStringUserAuditorAware();
    }

    @Bean
    public AuditorAware springSecurityOidcUuidUserAuditorAware() {
        return new SpringSecurityOidcUuidUserAuditorAware();
    }
}
