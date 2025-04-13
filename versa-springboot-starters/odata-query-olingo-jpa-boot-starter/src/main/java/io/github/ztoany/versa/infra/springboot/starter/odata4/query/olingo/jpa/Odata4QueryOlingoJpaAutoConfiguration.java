package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.olingo.jpa.processor.core.api.JPAODataRequestContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataServiceContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataSessionContextAccess;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor.OdataRequestCompositeInterceptor;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor.OdataRequestIEEE754CompatibleInterceptor;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor.OdataRequestInterceptor;
import jakarta.persistence.EntityManagerFactory;
import org.apache.olingo.commons.api.ex.ODataException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@AutoConfiguration
@ConditionalOnClass({HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(OdataJpaProperties.class)
@ComponentScan("io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.controller")
public class Odata4QueryOlingoJpaAutoConfiguration {
    @Bean
    public JPAODataSessionContextAccess sessionContext(
            OdataJpaProperties properties,
            EntityManagerFactory emf,
            ObjectMapper objectMapper)
            throws ODataException {
        String pUnitName = properties.getJpa().getPersistenceUnitName();
        List<String> typePackages = properties.getJpa().getTypePackages();

        return JPAODataServiceContext.with()
                .setPUnit(pUnitName)
                .setEntityManagerFactory(emf)
                .setTypePackage(typePackages.toArray(new String[0]))
                .setRequestMappingPath(properties.getPathMapping())
                .setEdmNameBuilder(new JPACustomEdmNameBuilder(pUnitName, properties.getJpa().getPropNameFirstToLower()))
                .setErrorProcessor(properties.getEnableProblemDetails() ? new ProblemDetailsErrorProcessor(objectMapper) : null)
                .build();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public JPAODataRequestContext requestContext() {
        return JPAODataRequestContext.with().build();
    }

    @Bean
    public OdataRequestInterceptor odataRequestInterceptor(OdataJpaProperties properties) {
        var interceptor = new OdataRequestCompositeInterceptor();
        interceptor.addInterceptor(new OdataRequestIEEE754CompatibleInterceptor(properties.getJpa().getForceIeee754Compatible()));
        return interceptor;
    }
}
