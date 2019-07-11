package ee.fbueth.playground;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SMSSender {
    private static final String CONTENT_TYPE = "content-type";

    private URI uri;
    private Token token;
    private HttpClient httpClient;

    public SMSSender(URI uri, Token token) {
        this.uri = uri;
        this.token = token;
        httpClient = HttpClient.newHttpClient();
    }

    public void send(SMS sms) {
        HttpRequest request = createHttpRequest(sms);
        HttpResponse response = execute(request);
        handleResponse(response);
    }

    private HttpRequest createHttpRequest(SMS sms) {
        String requestBody = sms.toJson();

        return HttpRequest.newBuilder()
                .uri(uri)
                .header(CONTENT_TYPE, "application/json")
                .header("auth", token.getTokenValue())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private HttpResponse execute(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IllegalStateException("Could not connect to server.", e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Connection to server interrupted.", e);
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
