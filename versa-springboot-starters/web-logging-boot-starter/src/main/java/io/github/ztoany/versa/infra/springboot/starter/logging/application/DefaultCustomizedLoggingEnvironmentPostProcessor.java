package io.github.ztoany.versa.infra.springboot.starter.logging.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.LogFile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;

/**
 *
 * EnvironmentPostProcessor implementations have to be registered in META-INF/spring.factories
 *
 */
@Order
public class DefaultCustomizedLoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static final String DEFAULT_CUSTOMIZED_LOGGING_CONFIG = "defaultCustomizedLoggingConfig";

    private static final String SPRING_APPLICATION_NAME_KEY = "spring.application.name";
    private static final String DEFAULT_SPRING_APPLICATION_NAME_VALUE = "spring";

    private static final String DEFAULT_LOGBACK_MAX_HISTORY_KEY = "logging.logback.rollingpolicy.max-history";

    private static final int DEFAULT_LOGBACK_MAX_HISTORY_VALUE = 30;

    private static final String DEFAULT_LOGBACK_MAX_FILE_SIZE_KEY = "logging.logback.rollingpolicy.max-file-size";

    private static final String DEFAULT_LOGBACK_MAX_FILE_SIZE_VALUE = "100MB";

    private static final String DEFAULT_LOG_FILE_PATH = "/var/log";

    private static final String ERROR_FILE_NAME_PROPERTY = "logging.error-file.name";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        /*
        if (environment.getPropertySources().contains(BootstrapApplicationListener.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            return;
        }
         */

        String appName = environment.getProperty(SPRING_APPLICATION_NAME_KEY);
        if(appName == null) {
            appName = DEFAULT_SPRING_APPLICATION_NAME_VALUE;
        }

        String sep = File.separator;
        String formatStr = "%s" + sep + "%s.log";

        HashMap<String, Object> map = new HashMap<>();

        String logFileName = environment.getProperty(LogFile.FILE_NAME_PROPERTY);
        if(logFileName == null) {
            String logFilePath = environment.getProperty(LogFile.FILE_PATH_PROPERTY);
            if(logFilePath == null) {
                logFilePath = DEFAULT_LOG_FILE_PATH;
            } else {
                logFilePath = StringUtils.trimTrailingCharacter(logFilePath, sep.charAt(0));
            }

            map.put(LogFile.FILE_NAME_PROPERTY, String.format(formatStr, logFilePath, appName));
        }

        String errorLogFileName = environment.getProperty(ERROR_FILE_NAME_PROPERTY);
        if(errorLogFileName == null) {
            String logFilePath = environment.getProperty(LogFile.FILE_PATH_PROPERTY);
            if(logFilePath == null) {
                logFilePath = DEFAULT_LOG_FILE_PATH;
            } else {
                logFilePath = StringUtils.trimTrailingCharacter(logFilePath, sep.charAt(0));
            }

            map.put(ERROR_FILE_NAME_PROPERTY, String.format(formatStr, logFilePath, appName + "-error"));
        }

        map.put(DEFAULT_LOGBACK_MAX_HISTORY_KEY, DEFAULT_LOGBACK_MAX_HISTORY_VALUE);
        map.put(DEFAULT_LOGBACK_MAX_FILE_SIZE_KEY, DEFAULT_LOGBACK_MAX_FILE_SIZE_VALUE);

        MapPropertySource target = (MapPropertySource)environment.getPropertySources().get(DEFAULT_CUSTOMIZED_LOGGING_CONFIG);
        if(target == null) {
            target = new MapPropertySource(DEFAULT_CUSTOMIZED_LOGGING_CONFIG, map);
        }
        environment.getPropertySources().addLast(target);
    }
}
