package ee.fbueth.playground.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class SMSConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
