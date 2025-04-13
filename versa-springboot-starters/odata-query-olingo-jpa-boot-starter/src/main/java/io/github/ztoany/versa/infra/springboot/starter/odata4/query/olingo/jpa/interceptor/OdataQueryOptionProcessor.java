package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;

import java.util.List;

public interface OdataQueryOptionProcessor {
    List<QueryOption> process(OdataHttpServletRequestWrapper requestWrapper, List<QueryOption> queryOptions);
}
