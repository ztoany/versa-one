package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.processor.ErrorProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ProblemDetailsErrorProcessor implements ErrorProcessor {
    private OData odata;
    private ServiceMetadata serviceMetadata;
    private final ObjectMapper objectMapper;

    public ProblemDetailsErrorProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    @Override
    public void processError(final ODataRequest request, final ODataResponse response,
                             final ODataServerError serverError,
                             final ContentType requestedContentType) {
        CustomProblemDetail problemDetail = new CustomProblemDetail();
        problemDetail.setStatus(serverError.getStatusCode());
        problemDetail.setDetail(serverError.getMessage());
        problemDetail.updateTimestampToNow();
        problemDetail.setInstance(URI.create(request.getRawServiceResolutionUri() + request.getRawODataPath()));
        try {
            var prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(problemDetail);
            response.setContent(new ByteArrayInputStream(prettyJson.getBytes(StandardCharsets.UTF_8)));
            response.setStatusCode(serverError.getStatusCode());
            response.setHeader(HttpHeader.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        } catch (JsonProcessingException e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            CustomProblemDetail pb = new CustomProblemDetail();
            pb.setStatus(status);
            pb.setDetail(status.getReasonPhrase());
            pb.updateTimestampToNow();
            pb.setInstance(URI.create(request.getRawServiceResolutionUri() + request.getRawODataPath()));

            response.setContent(new ByteArrayInputStream(pb.jsonString().getBytes(StandardCharsets.UTF_8)));
            response.setStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode());
            response.setHeader(HttpHeader.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        }
    }
}
