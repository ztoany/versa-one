package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.id.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "snowflake-id")
public class SnowflakeIdProperties {
    private int datacenterId = 0;
    private int workerId = 0;

    public int getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(int datacenterId) {
        this.datacenterId = datacenterId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}
