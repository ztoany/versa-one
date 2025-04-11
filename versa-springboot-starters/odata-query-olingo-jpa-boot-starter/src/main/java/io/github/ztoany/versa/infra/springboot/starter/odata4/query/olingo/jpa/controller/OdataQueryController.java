package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.controller;

import com.sap.olingo.jpa.processor.core.api.JPAODataRequestContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataRequestHandler;
import com.sap.olingo.jpa.processor.core.api.JPAODataSessionContextAccess;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.OdataJpaProperties;
import io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.requestwrapper.AcceptHeaderHttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.format.AcceptType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OdataQueryController {
    private final boolean forceIeee754Compatible;
    private final JPAODataSessionContextAccess sessionContext;
    private final JPAODataRequestContext requestContext;

    public OdataQueryController(
            OdataJpaProperties properties,
            JPAODataSessionContextAccess sessionContext,
            JPAODataRequestContext requestContext) {
        this.forceIeee754Compatible = properties.getJpa().getForceIeee754Compatible();
        this.sessionContext = sessionContext;
        this.requestContext = requestContext;
    }

    @RequestMapping(value = "${odata.path-mapping:/api/odata}/**", method = { RequestMethod.GET })
    public void query(HttpServletRequest req, final HttpServletResponse resp) throws ODataException {
        if(forceIeee754Compatible) {
            req = new AcceptHeaderHttpServletRequestWrapper(req, buildOdataIEEE754CompatibleHeader(req));
        }
        new JPAODataRequestHandler(sessionContext, requestContext).process(req, resp);
    }

    private String buildOdataIEEE754CompatibleHeader(final HttpServletRequest req) {
        //var compatibleHeader = ContentType.create(ContentType.APPLICATION_JSON, ContentType.PARAMETER_IEEE754_COMPATIBLE, Boolean.TRUE.toString());
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
