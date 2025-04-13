package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.http.ProblemDetail;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomProblemDetail extends ProblemDetail {
    private static final String JSON_FORMAT = """
            {
              "type": "%s",
              "title": "%s",
              "status": %d,
              "detail": "%s",
              "instance": "%s"%s
              %s
            }
            """;

    public void updateTimestampToNow() {
        super.setProperty("timestamp", Instant.now());
    }

    @Override
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return super.getProperties();
    }

    public String jsonString() {
        String propStr = propertiesJsonString();
        return String.format(JSON_FORMAT,
                getType(), getTitle(), getStatus(), getDetail(),
                getInstance(), propStr.isEmpty() ? "" : ",", propertiesJsonString());
    }

    private String propertiesJsonString() {
        var map = getProperties();
        if(map == null || map.isEmpty()) {
            return "";
        } else {
            return map.entrySet().stream()
                    .map(e -> String.format("\"%s\": \"%s\"", e.getKey(), e.getValue().toString()))
                    .collect(Collectors.joining(",\n"));
        }
    }
}
