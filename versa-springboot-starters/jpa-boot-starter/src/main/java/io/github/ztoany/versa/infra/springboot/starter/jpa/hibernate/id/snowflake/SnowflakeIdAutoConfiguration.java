package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.id.snowflake;

import io.github.ztoany.versa.infra.common.id.snowflake.SnowflakeIdImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({HibernateJpaAutoConfiguration.class, SnowflakeIdImpl.class})
@EnableConfigurationProperties(SnowflakeIdProperties.class)
public class SnowflakeIdAutoConfiguration {
    private final SnowflakeIdProperties properties;

    public SnowflakeIdAutoConfiguration(SnowflakeIdProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SnowflakeIdImpl globalSnowflakeId() {
        return new SnowflakeIdImpl(properties.getDatacenterId(), properties.getWorkerId());
    }
}
