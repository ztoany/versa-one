package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.controller;

import com.sap.olingo.jpa.processor.core.api.JPAODataRequestContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataRequestHandler;
import com.sap.olingo.jpa.processor.core.api.JPAODataSessionContextAccess;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataHttpServletRequestWrapper;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor.OdataRequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OdataQueryController {
    private final JPAODataSessionContextAccess sessionContext;
    private final JPAODataRequestContext requestContext;
    private final OdataRequestInterceptor requestInterceptor;

    private static final String REQUESTMAPPING = "requestMapping";

    public OdataQueryController(
            JPAODataSessionContextAccess sessionContext,
            JPAODataRequestContext requestContext,
            OdataRequestInterceptor requestInterceptor) {
        this.sessionContext = sessionContext;
        this.requestContext = requestContext;
        this.requestInterceptor = requestInterceptor;
    }

    @RequestMapping(value = "${odata.path-mapping:/api/odata}/**", method = { RequestMethod.GET })
    public void query(final HttpServletRequest req, final HttpServletResponse resp) throws ODataException {
        // this attribute is used for resolve the name of EdmType from url.
        // see OdataFilterOptionByEdmTypeProcessor::getEdmTypeName
        // it's also be set in ODataHttpHandlerImpl::fillUriInformation
        req.setAttribute(REQUESTMAPPING, sessionContext.getMappingPath());
        var reqWrapper = new OdataHttpServletRequestWrapper(req, req.getHeader(HttpHeader.ACCEPT), req.getQueryString());
        requestInterceptor.invoke(reqWrapper);
        new JPAODataRequestHandler(sessionContext, requestContext).process(reqWrapper, resp);
    }
}
