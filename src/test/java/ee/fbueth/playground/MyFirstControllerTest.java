package ee.fbueth.playground;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PlaygroundApplication.class})
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
class MyFirstControllerTest {

    @LocalServerPort
    int port;

    @Test
    void greets_users() {
        given().
            port(port).
            queryParam("name", "Joshua").
        when().
            get("/greeting").
        then().
            statusCode(200).assertThat().body(equalTo("Hello Joshua"));
    }

}
