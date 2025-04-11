package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.requestwrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.olingo.commons.api.http.HttpHeader;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class AcceptHeaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String acceptHeader;
    public AcceptHeaderHttpServletRequestWrapper(HttpServletRequest request, String acceptHeader) {
        super(request);
        this.acceptHeader = acceptHeader;
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
}
