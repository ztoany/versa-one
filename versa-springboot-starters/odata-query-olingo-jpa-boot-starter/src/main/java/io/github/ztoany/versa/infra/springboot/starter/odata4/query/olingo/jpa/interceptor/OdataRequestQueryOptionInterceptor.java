package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.UriDecoder;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;
import org.apache.olingo.server.core.uri.parser.UriParserSyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OdataRequestQueryOptionInterceptor implements OdataRequestInterceptor {
    private final List<OdataQueryOptionProcessor> processors = new ArrayList<>();

    public void addProcessor(OdataQueryOptionProcessor processor) {
        processors.add(processor);
    }

    @Override
    public void invoke(OdataHttpServletRequestWrapper requestWrapper) {
        if(processors.isEmpty()) {
            return;
        }

        List<QueryOption> list = splitAndDecodeOptions(requestWrapper.getQueryString());

        for (OdataQueryOptionProcessor processor : processors) {
            list = processor.process(requestWrapper, list);
        }

        String ret = list.stream()
                .map(e -> e.getName() + "=" + e.getText())
                .collect(Collectors.joining("&"));

        requestWrapper.setQueryString(ret);
    }

    protected List<QueryOption> splitAndDecodeOptions(String queryString) {
        try {
            return queryString == null ? Collections.emptyList() : UriDecoder.splitAndDecodeOptions(queryString);
        } catch (UriParserSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
