package ee.fbueth.playground.messagingAuth;

import ee.fbueth.playground.PlaygroundApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PlaygroundApplication.class, TestConfig.class})
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
class AuthController_should {

    @LocalServerPort
    int port;

    @Test
    void return_token_when_get_request_with_header_received() {
        given().
            port(port).
            header("sender", "123").
        when().
            get("/messaging/auth").
        then().
            statusCode(200).assertThat().body(equalTo("abcde"));
    }
}