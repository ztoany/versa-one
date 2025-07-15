package io.github.ztoany.versa.infra.springboot.auditing;

import java.util.Optional;

public interface UserAuditing<T> {
    Optional<T> getCurrentUserId();
}
