package ee.fbueth.playground.messaging;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

class TokenReceiver_should {

    private MockWebServer mockWebServer;
    private TokenReceiver tokenReceiver;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        MyAppConfig configuration = new MyAppConfig();
        configuration.setTokenUrl(mockWebServer.url("/").toString());
        tokenReceiver = new TokenReceiver(configuration, HttpClient.newHttpClient());
    }

    @Test
    void make_get_request_to_url_from_parameter() throws Exception {
        //given
        givenServerRespondingWithStatus200();

        //when
        tokenReceiver.requestToken("123");

        //then
        RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertEquals("GET", recordedRequest.getMethod());
    }

    private void givenServerRespondingWithStatus200() {
        mockWebServer.enqueue(new MockResponse());
    }

    @Test
    void make_request_with_sender_header() throws Exception {
        //given
        givenServerRespondingWithStatus200();

        //when
        tokenReceiver.requestToken("123");

        //then
        RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertEquals("123", recordedRequest.getHeader("sender"));
    }

    @Test
    void return_a_token_when_making_valid_request() {
        //given
        mockWebServer.enqueue(new MockResponse().setBody("abc"));

        //when
        Token token = tokenReceiver.requestToken("123");

        //then
        assertEquals("123_abc", token.getTokenValue());
    }
}