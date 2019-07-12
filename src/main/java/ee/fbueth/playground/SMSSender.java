package ee.fbueth.playground;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class SMSSender {
    private static final String CONTENT_TYPE = "content-type";

    private HttpClient httpClient;
    private MyAppConfig configuration;

    public SMSSender(MyAppConfig configuration, HttpClient httpClient) {
        this.configuration = configuration;
        this.httpClient = httpClient;
    }

    public void send(SMS sms, Token token) {
        HttpRequest request = createHttpRequest(sms, token);
        HttpResponse response = execute(request);
        handleResponse(response);
    }

    private HttpRequest createHttpRequest(SMS sms, Token token) {
        String requestBody = sms.toJson();

        URI uri;
        try {
            uri = new URI (configuration.getSmsUrl());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Not a valid URL.", e);
        }

        return HttpRequest.newBuilder()
                .uri(uri)
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("auth", token.getTokenValue())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private HttpResponse execute(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Could not connect to server.", e);
        }
    }

    private void handleResponse(HttpResponse response) {
        int statusCode = response.statusCode();

        if (isResponseCode3xx(statusCode)) {
            throw new IllegalArgumentException(statusCode + " - Redirected: " + response.body());
        }

        if (isResponseCode4xx(statusCode)) {
            throw new IllegalArgumentException(statusCode + " - Client Error: " + response.body());
        }

        if (isResponseCode5xx(statusCode)) {
            throw new IllegalStateException(statusCode + " - Server Error: " + response.body());
        }
    }

    private boolean isResponseCode3xx(int statusCode) {
        return statusCode > 299 && statusCode <= 399;
    }

    private boolean isResponseCode4xx(int statusCode) {
        return statusCode > 399 && statusCode <= 499;
    }

    private boolean isResponseCode5xx(int statusCode) {
        return statusCode > 499;
    }
}
