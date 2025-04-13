package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.olingo.commons.api.format.AcceptType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OdataRequestIEEE754CompatibleInterceptor implements OdataRequestInterceptor {
    private final boolean forceIeee754Compatible;
    public OdataRequestIEEE754CompatibleInterceptor(boolean forceIeee754Compatible) {
        this.forceIeee754Compatible = forceIeee754Compatible;
    }

    @Override
    public void invoke(OdataHttpServletRequestWrapper requestWrapper) {
        if(forceIeee754Compatible) {
            requestWrapper.setAcceptHeader(buildOdataIEEE754CompatibleHeader(requestWrapper));
        }
    }

    private String buildOdataIEEE754CompatibleHeader(final HttpServletRequest req) {
        var acceptHeader = req.getHeader(HttpHeader.ACCEPT);
        List<AcceptType> oldList = AcceptType.create(acceptHeader);
        List<AcceptType> retList = new ArrayList<>();
        for (AcceptType acceptType : oldList) {
            if(acceptType.getType().equals(ContentType.APPLICATION_JSON.getType())
                    && acceptType.getSubtype().equals(ContentType.APPLICATION_JSON.getSubtype())) {
                var compatibleStr = String.format("%s;%s=%s", acceptType, ContentType.PARAMETER_IEEE754_COMPATIBLE, Boolean.TRUE);
                retList.addAll(AcceptType.create(compatibleStr));
            } else {
                retList.add(acceptType);
            }
        }

        return retList.stream().map(AcceptType::toString).collect(Collectors.joining(","));
    }
}
