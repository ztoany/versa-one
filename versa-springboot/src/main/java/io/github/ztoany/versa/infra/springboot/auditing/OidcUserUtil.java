package io.github.ztoany.versa.infra.springboot.auditing;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public class OidcUserUtil {
    public static Optional<String> getOidcSubjectId() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(OidcUser.class::cast)
                .map(OidcUser::getUserInfo)
                .map(OidcUserInfo::getClaims)
                .map(e -> (String)e.get("sub"));
    }
}
