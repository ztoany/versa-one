package io.github.ztoany.versa.infra.springboot.auditing;

import java.util.Optional;

public class OidcStringUserAuditing implements StringUserAuditing {
    @Override
    public Optional<String> getCurrentUserId() {
        return OidcUserUtil.getOidcSubjectId();
    }
}
