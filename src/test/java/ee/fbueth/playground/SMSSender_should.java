package ee.fbueth.playground;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

class SMSSender_should {

    private MockWebServer mockWebServer;
    private SMSSender smsSender;
    private SMS sms;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        URI uri = new URI(mockWebServer.url("/").toString());
        sms = new SMS.SMSBuilder().from("123").withName("Florian").to("987").withText("Proekspert").build();
        String token = "abc";
        smsSender = new SMSSender(uri, token);
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
        assertEquals("abc", recordedRequest.getHeader("token"));
    }
}