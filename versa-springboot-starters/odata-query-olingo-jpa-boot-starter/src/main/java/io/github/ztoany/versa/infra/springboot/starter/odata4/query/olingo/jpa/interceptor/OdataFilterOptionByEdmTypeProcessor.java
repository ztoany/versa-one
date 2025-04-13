package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class OdataFilterOptionByEdmTypeProcessor extends OdataFilterOptionProcessor {
    private static final String REQUESTMAPPING = "requestMapping";
    private final Map<String, BiFunction<OdataHttpServletRequestWrapper, String, String>> edmTypeOperationMap = new HashMap<>();

    @Override
    public String processText(OdataHttpServletRequestWrapper requestWrapper, String text) {
        var edmTypeName = getEdmTypeName(requestWrapper);
        var operation = edmTypeOperationMap.get(edmTypeName);
        return operation != null ? operation.apply(requestWrapper, text) : text;
    }

    public void registerOperation(String edmType, BiFunction<OdataHttpServletRequestWrapper, String, String> operation) {
        edmTypeOperationMap.put(edmType, operation);
    }

    protected String getEdmTypeName(OdataHttpServletRequestWrapper requestWrapper) {
        String rawRequestUri = requestWrapper.getRequestURL().toString();
        String rawServiceResolutionUri = null;
        String rawODataPath;

        if(requestWrapper.getAttribute(REQUESTMAPPING)!=null){
            String requestMapping = requestWrapper.getAttribute(REQUESTMAPPING).toString();
            rawServiceResolutionUri = requestMapping;
            int beginIndex = rawRequestUri.indexOf(requestMapping) + requestMapping.length();
            rawODataPath = rawRequestUri.substring(beginIndex);
        }else if(!"".equals(requestWrapper.getServletPath())) {
            int beginIndex = rawRequestUri.indexOf(requestWrapper.getServletPath()) +
                    requestWrapper.getServletPath().length();
            rawODataPath = rawRequestUri.substring(beginIndex);
        } else if (!"".equals(requestWrapper.getContextPath())) {
            int beginIndex = rawRequestUri.indexOf(requestWrapper.getContextPath()) +
                    requestWrapper.getContextPath().length();
            rawODataPath = rawRequestUri.substring(beginIndex);
        } else {
            rawODataPath = requestWrapper.getRequestURI();
        }

        if(rawODataPath == null || rawODataPath.isEmpty()){
            return "";
        }

        return rawODataPath.substring(1);
    }
}
