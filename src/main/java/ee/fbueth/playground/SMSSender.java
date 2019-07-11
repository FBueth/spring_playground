package ee.fbueth.playground;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SMSSender {

    URI uri;
    String token;
    HttpClient httpClient;

    public SMSSender(URI uri, String token) {
        this.uri = uri;
        this.token = token;
        httpClient = HttpClient.newHttpClient();
    }

    public void send(SMS sms) {
        HttpRequest httpRequest = createHttpRequest(sms);
        HttpResponse httpResponse = execute(httpRequest);
        handleResponse(httpResponse);
    }

    private HttpRequest createHttpRequest(SMS sms) {
        String requestBody = sms.toJson();

        return HttpRequest.newBuilder()
                .uri(uri)
                .header("token", token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private HttpResponse execute(HttpRequest httpRequest) {
        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IllegalStateException("Could not connect to server", e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Connection to server interrupted", e);
        }
    }

    private void handleResponse(HttpResponse httpResponse) {

    }
}
