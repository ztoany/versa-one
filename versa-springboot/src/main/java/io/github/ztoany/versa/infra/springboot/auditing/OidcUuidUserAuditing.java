package io.github.ztoany.versa.infra.springboot.auditing;

import java.util.Optional;
import java.util.UUID;

public class OidcUuidUserAuditing implements UuidUserAuditing {
    @Override
    public Optional<UUID> getCurrentUserId() {
        var op = OidcUserUtil.getOidcSubjectId();
        return op.map(UUID::fromString);
    }
}
