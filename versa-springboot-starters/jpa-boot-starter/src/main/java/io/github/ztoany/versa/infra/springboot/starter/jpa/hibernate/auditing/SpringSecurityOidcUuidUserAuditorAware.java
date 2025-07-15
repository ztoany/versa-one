package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.auditing;

import io.github.ztoany.versa.infra.springboot.auditing.OidcUuidUserAuditing;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class SpringSecurityOidcUuidUserAuditorAware implements AuditorAware<UUID> {

    private final OidcUuidUserAuditing userAuditing = new OidcUuidUserAuditing();

    @Override
    public Optional<UUID> getCurrentAuditor() {
        return userAuditing.getCurrentUserId();
    }
}
