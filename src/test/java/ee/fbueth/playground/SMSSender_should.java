package ee.fbueth.playground;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SMSSender_should {

    private MockWebServer mockWebServer;
    private SMSSender smsSender;
    private SMS sms;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        URI uri = new URI(mockWebServer.url("/").toString());
        Token token = new Token("123", "abc");
        smsSender = new SMSSender(uri, token);
        sms = new SMS.SMSBuilder().from("123456789").withName("Me").to("987654321").withText("Hello world!").build();
    }

    @Test
    void make_a_post_request_to_uri_from_parameter() throws Exception {
        //given
        givenServerRespondingWithStatus200();

        //when
        smsSender.send(sms);

        //then
        RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertEquals("POST", recordedRequest.getMethod());
    }

    private void givenServerRespondingWithStatus200() {
        mockWebServer.enqueue(new MockResponse());
    }

    @Test
    void send_request_with_token_in_header() throws Exception {
        //given
        givenServerRespondingWithStatus200();

        //when
        smsSender.send(sms);

        //then
        RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS);
        assertEquals("123_abc", recordedRequest.getHeader("auth"));
    }

    @Test
    void throw_exception_when_connection_to_server_failed() throws Exception {
        //given
        mockWebServer.shutdown();

        //when
        try {
            smsSender.send(sms);
            fail("Should have thrown exception when connection to server failed");
        } catch (IllegalStateException e) {
            assertEquals("Could not connect to server.", e.getMessage());
        }
    }

    @Test
    void throw_illegal_state_exception_when_server_says_500() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("NullPointer... :D"));

        // when
        try {
            smsSender.send(sms);
            fail("should have thrown exception when server does not respond with status 200");
        } catch (IllegalStateException e) {
            assertEquals("500 - Server Error: NullPointer... :D", e.getMessage());
        }
    }

    @Test
    void throw_illegal_argument_exception_when_server_says_400() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(400).setBody("bad request my friend"));

        // when
        try {
            smsSender.send(sms);
            fail("should have thrown exception when server does not respond with status 200");
        } catch (IllegalArgumentException e) {
            assertEquals("400 - Client Error: bad request my friend", e.getMessage());
        }
    }

    @Test
    void throw_illegal_state_exception_when_server_says_300() {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(300).setBody("What is this"));

        // when
        try {
            smsSender.send(sms);
            fail("should have thrown exception when server does not respond with status 200");
        } catch (IllegalArgumentException e) {
            assertEquals("300 - Redirected: What is this", e.getMessage());
        }
    }
}