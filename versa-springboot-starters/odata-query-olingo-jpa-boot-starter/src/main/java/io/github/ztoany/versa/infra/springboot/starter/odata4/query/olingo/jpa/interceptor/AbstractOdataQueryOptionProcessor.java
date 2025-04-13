package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;
import org.apache.olingo.server.core.uri.queryoption.CustomQueryOptionImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOdataQueryOptionProcessor implements OdataQueryOptionProcessor {
    public List<QueryOption> process(OdataHttpServletRequestWrapper requestWrapper, List<QueryOption> queryOptions) {
        List<QueryOption> ret = new ArrayList<>();
        for (QueryOption queryOption : queryOptions) {
            if (queryOption.getName().equals(getQueryOptionName())) {
                ret.add(new CustomQueryOptionImpl()
                        .setName(queryOption.getName())
                        .setText(processText(requestWrapper, queryOption.getText()))
                );
            } else {
                ret.add(queryOption);
            }
        }
        return ret;
    }

    abstract String getQueryOptionName();
    abstract String processText(OdataHttpServletRequestWrapper requestWrapper, String text);
}
