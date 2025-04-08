package io.github.ztoany.versa.infra.springboot.starter.hibernate.id.snowflake;

import io.github.ztoany.versa.infra.common.id.snowflake.SnowflakeIdImpl;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.hibernate.resource.beans.container.spi.BeanContainer;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;
import org.springframework.orm.hibernate5.SpringBeanContainer;

import java.lang.reflect.Member;
import java.util.EnumSet;

import static io.github.ztoany.versa.infra.springboot.starter.hibernate.id.snowflake.SnowflakeId.Style.SINGLETON;
import static org.hibernate.generator.EventTypeSets.INSERT_ONLY;

public class SnowflakeIdGenerator implements BeforeExecutionGenerator {
    private final SnowflakeIdImpl id;
    private static final BeanContainer.LifecycleOptions lifecycleOptions = new SnowflakeIdLifecycleOptions();

    private static final String GLOBAL_SNOWFLAKE_ID_BEAN_NAME = "globalSnowflakeId";

    public SnowflakeIdGenerator(
            SnowflakeId config,
            Member idMember,
            CustomIdGeneratorCreationContext creationContext) {
        SpringBeanContainer beanContainer = (SpringBeanContainer)creationContext.getServiceRegistry().getService(ManagedBeanRegistry.class).getBeanContainer();
        if(config.style() == SINGLETON) {
            id = beanContainer.getBean(GLOBAL_SNOWFLAKE_ID_BEAN_NAME, SnowflakeIdImpl.class, lifecycleOptions, FallbackBeanInstanceProducer.INSTANCE).getBeanInstance();
        } else {
            SnowflakeIdProperties properties = beanContainer.getBean(SnowflakeIdProperties.class, lifecycleOptions, FallbackBeanInstanceProducer.INSTANCE).getBeanInstance();
            id = new SnowflakeIdImpl(properties.getDatacenterId(), properties.getWorkerId());
        }
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return INSERT_ONLY;
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) throws HibernateException {
        return id.nextId();
    }
}
