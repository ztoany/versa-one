package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;

import java.util.ArrayList;
import java.util.List;

public class OdataRequestCompositeInterceptor implements OdataRequestInterceptor {
    private final List<OdataRequestInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(OdataRequestInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public void invoke(OdataHttpServletRequestWrapper requestWrapper) {
        interceptors.forEach(interceptor -> interceptor.invoke(requestWrapper));
    }
}
