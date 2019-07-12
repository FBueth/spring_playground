package ee.fbueth.playground.messaging;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TokenReceiver {

    private HttpClient httpClient;
    private MyAppConfig configuration;

    public TokenReceiver(MyAppConfig configuration, HttpClient httpClient) {
        this.configuration = configuration;
        this.httpClient = httpClient;
    }

    public Token requestToken(String sender) {
        HttpRequest request = createHttpRequest(sender);
        HttpResponse<String> response = execute(request);
        String tokenResponse = response.body();
        return new Token(sender, tokenResponse);
    }

    private HttpRequest createHttpRequest(String sender) {
        URI uri;
        try {
            uri = new URI(configuration.getTokenUrl());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Not a valid URL.", e);
        }
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("sender", sender)
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
