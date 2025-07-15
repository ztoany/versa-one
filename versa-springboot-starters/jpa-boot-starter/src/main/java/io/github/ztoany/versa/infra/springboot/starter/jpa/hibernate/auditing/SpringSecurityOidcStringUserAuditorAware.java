package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.auditing;

import io.github.ztoany.versa.infra.springboot.auditing.OidcStringUserAuditing;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityOidcStringUserAuditorAware implements AuditorAware<String> {

    private final OidcStringUserAuditing userAuditing = new OidcStringUserAuditing();

    @Override
    public Optional<String> getCurrentAuditor() {
        return userAuditing.getCurrentUserId();
    }
}
