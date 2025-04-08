package io.github.ztoany.versa.infra.springboot.starter.localization;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocaleUtils {
    public static Locale handleHttpAcceptLanguage() {
        return LocaleContextHolder.getLocale();
    }
}
