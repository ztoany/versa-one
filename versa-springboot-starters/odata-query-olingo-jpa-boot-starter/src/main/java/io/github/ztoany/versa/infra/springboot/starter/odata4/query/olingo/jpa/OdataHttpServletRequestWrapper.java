package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.olingo.commons.api.http.HttpHeader;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class OdataHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private String acceptHeader;
    private String queryString;
    public OdataHttpServletRequestWrapper(
            HttpServletRequest request,
            String acceptHeader,
            String queryString) {
        super(request);
        this.acceptHeader = acceptHeader;
        this.queryString = queryString;
    }

    @Override
    public String getHeader(String name) {
        if(HttpHeader.ACCEPT.equalsIgnoreCase(name)) {
            return acceptHeader;
        } else {
            return super.getHeader(name);
        }
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if(HttpHeader.ACCEPT.equalsIgnoreCase(name)) {
            return Collections.enumeration(Arrays.asList(acceptHeader));
        } else {
            return super.getHeaders(name);
        }
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    public void setAcceptHeader(String acceptHeader) {
        this.acceptHeader = acceptHeader;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
