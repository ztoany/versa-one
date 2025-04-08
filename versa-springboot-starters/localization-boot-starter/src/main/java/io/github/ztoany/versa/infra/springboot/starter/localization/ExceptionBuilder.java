package io.github.ztoany.versa.infra.springboot.starter.localization;

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
        var msg = messageSource.getMessage(code, null, LocaleUtils.handleHttpAcceptLanguage());
        return new SystemException(code, msg);
    }

    public static SystemException systemException(String code, Locale locale) {
        var msg = messageSource.getMessage(code, null, locale);
        return new SystemException(code, msg);
    }

    public static BusinessException businessException(String code) {
        var msg = messageSource.getMessage(code, null, LocaleUtils.handleHttpAcceptLanguage());
        return new BusinessException(code, msg);
    }

    public static BusinessException businessException(String code, Locale locale) {
        var msg = messageSource.getMessage(code, null, locale);
        return new BusinessException(code, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, String id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, String id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Long id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id == null ? id : id.toString()}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Long id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id == null ? id : id.toString()}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Integer id) {
        var locale = LocaleUtils.handleHttpAcceptLanguage();
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }

    public static EntityNotFoundException entityNotFoundException(String entityName, Integer id, Locale locale) {
        var entityMsg = messageSource.getMessage(entityName, null, locale);
        var msg = messageSource.getMessage(PredefinedErrorCodes.ENTITY_NOT_FOUND, new Object[]{entityMsg, id}, locale);
        return new EntityNotFoundException(PredefinedErrorCodes.ENTITY_NOT_FOUND, msg);
    }
}
