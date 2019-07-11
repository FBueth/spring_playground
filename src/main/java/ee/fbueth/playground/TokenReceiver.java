package ee.fbueth.playground;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenReceiver {
    private URI uri;
    private HttpClient httpClient;

    public TokenReceiver(URI uri) {
        this.uri = uri;
        this.httpClient = HttpClient.newBuilder().build();
    }

    public String requestToken(String sender) {
        HttpRequest request = createHttpRequest(sender);
        HttpResponse<String> response = execute(request);
        return response.body();
    }

    private HttpRequest createHttpRequest(String sender) {
        return HttpRequest.newBuilder()
                .uri(this.uri)
                .header("Sender", sender)
                .GET()
                .build();
    }

    private HttpResponse<String> execute(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IllegalStateException("Error, could not connect to server.", e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Error, something was interrupted.", e);
        }
    }
}