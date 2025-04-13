package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;

public interface OdataRequestInterceptor {
    void invoke(OdataHttpServletRequestWrapper requestWrapper);
}
