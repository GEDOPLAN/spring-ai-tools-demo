package de.gedoplan.showcase.springaidemo.config;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);

    private final boolean logRequests;

    private final boolean logResponses;

    public HttpLoggingInterceptor() {
        this.logRequests = true;
        this.logResponses = true;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] requestBody, ClientHttpRequestExecution execution)
            throws IOException {
        if (logRequests) {
            logRequest(request, requestBody);
        }

        if (logResponses) {
            return logResponse(request, requestBody, execution);
        }
        else {
            return execution.execute(request, requestBody);
        }
    }

    private void logRequest(HttpRequest request, byte[] requestBody) {
        String requestString = new String(requestBody, StandardCharsets.UTF_8);
        if (request.getHeaders().getContentType() != null &&
                request.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
            requestString = new JSONObject(requestString).toString(4);
        }
        logger.debug("Request:\n Method: {}.\n URI: {}.\n Headers: {}.\n Body: {}", request.getMethod(),
                request.getURI(),
                request.getHeaders()
                        .toSingleValueMap()
                        .entrySet()
                        .stream()
                        .filter(e -> !e.getKey().equals(HttpHeaders.AUTHORIZATION))
                        .map(e -> e.getKey() + ":" + e.getValue())
                        .collect(Collectors.joining(", ")),
                requestString
        );
    }

    private ClientHttpResponse logResponse(HttpRequest request, byte[] requestBody,
                                           ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, requestBody);
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        if (response.getHeaders().getContentType() != null &&
                response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
            responseBody = new JSONObject(responseBody).toString(4);
        }

        logger.debug("Response:\n Status Code: {}.\n Headers: {}.\n Body: {}", response.getStatusText(),
                response.getHeaders()
                        .toSingleValueMap()
                        .entrySet()
                        .stream()
                        .map(e -> e.getKey() + ":" + e.getValue())
                        .collect(Collectors.joining(", ")),
                responseBody);

        return response;
    }

}