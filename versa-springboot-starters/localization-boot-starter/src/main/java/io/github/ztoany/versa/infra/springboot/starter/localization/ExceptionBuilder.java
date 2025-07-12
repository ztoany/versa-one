package io.github.ztoany.versa.infra.springboot.starter.localization;

import io.github.ztoany.versa.infra.common.exception.AuthenticationFailedException;
import io.github.ztoany.versa.infra.common.exception.BusinessException;
import io.github.ztoany.versa.infra.common.exception.EntityNotFoundException;
import io.github.ztoany.versa.infra.common.exception.SystemException;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ExceptionBuilder {
    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        ExceptionBuilder.messageSource = messageSource;
    }

    public static SystemException systemException(String code) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return systemException(code, locale);
    }

    public static SystemException systemException(String code, Locale locale) {
        var msg = messageSource.getMessage(code, null, locale);
        return new SystemException(code, msg);
    }

    public static BusinessException businessException(String code) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return businessException(code, locale);
    }

    public static BusinessException businessException(String code, Locale locale) {
        var msg = messageSource.getMessage(code, null, locale);
        return new BusinessException(code, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, String id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return entityNotFoundException(entityName, id, locale);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, String id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Long id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return entityNotFoundException(entityName, id, locale);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Long id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Integer id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return entityNotFoundException(entityName, id, locale);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Integer id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static AuthenticationFailedException authFailedException() {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        return authFailedException(locale);
    }

    public static AuthenticationFailedException authFailedException(Locale locale) {
        var msg = messageSource.getMessage(PredefinedErrorCodes.AUTH_FAILED, new Object[]{}, locale);
        return new AuthenticationFailedException(PredefinedErrorCodes.AUTH_FAILED, msg);
    }
}
