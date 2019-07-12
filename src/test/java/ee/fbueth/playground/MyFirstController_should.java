package ee.fbueth.playground;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PlaygroundApplication.class})
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
class MyFirstController_should {

    @LocalServerPort
    int port;

    @Test
    void greet_users_by_name() {
        given().
            port(port).
            queryParam("name", "Joshua").
        when().
            get("/greeting").
        then().
            statusCode(200).assertThat().body(equalTo("Hello Joshua"));
    }

    @Test
    void greet_users() {
        given().
                port(port).
                when().
                get("/greeting").
                then().
                statusCode(200).assertThat().body(equalTo("Hello there"));
    }

    @Autowired
    private MyAppConfig configuration;

    @Test
    void return_ok_when_send_endpoint_hit() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        mockWebServer.enqueue(new MockResponse().setBody("anytoken"));
        mockWebServer.enqueue(new MockResponse());

        String url = mockWebServer.url("/").toString();
        configuration.setTokenUrl(url);
        configuration.setSmsUrl(url);

        given().
            port(port).
            header("content-type", MediaType.APPLICATION_JSON_VALUE).
            request().body("{\"senderNumber\":\"123\",\"senderName\":\"Florian\",\"receiver\":\"987\",\"text\":\"Proekspert\"}").
        when().
            post("/send").
        then().
            statusCode(200).assertThat().body(equalTo("Ok"));
    }

}
