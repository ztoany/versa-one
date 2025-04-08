package io.github.ztoany.versa.infra.springboot.starter.logging.tomcat;


import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass({ Tomcat.class, UpgradeProtocol.class })
public class TomcatAccessLogDefaultValueProcessor implements InstantiationAwareBeanPostProcessor {
    @Value("${spring.application.name:spring}")
    private String applicationName;

    private static final String DIR = "/var/log";
    private static final String ACCESS_LOG_TYPE = "-access";
    private static final String FILE_DATE_FORMAT = "-yyyy-MM-dd";
    private static final String UTF8 = "utf-8";
    private static final int MAX_DAYS = 30;

    private static final String DEFAULT_PATTERN = "%a %l %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-Agent}i\" \"%{X-Forwarded-For}i\" %F %D %h %{X-Request-Id}i";

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean instanceof ServerProperties serverProperties) {
            ServerProperties.Tomcat.Accesslog accesslog = serverProperties.getTomcat().getAccesslog();
            accesslog.setEnabled(true);
            accesslog.setDirectory(DIR);
            accesslog.setPrefix(applicationName + ACCESS_LOG_TYPE);
            accesslog.setBuffered(true);
            accesslog.setFileDateFormat(FILE_DATE_FORMAT);
            accesslog.setMaxDays(MAX_DAYS);
            accesslog.setPattern(DEFAULT_PATTERN);
            accesslog.setEncoding(UTF8);
            accesslog.setRotate(true);
            accesslog.setRenameOnRotate(true);
        }

        return true;
    }
}
