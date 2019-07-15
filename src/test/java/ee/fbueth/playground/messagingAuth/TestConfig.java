package ee.fbueth.playground.messagingAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public TokenCreator tokenCreator() {
        return new TokenCreator() {
            @Override
            public String create() {
                return "abcde";
            }
        };
    }
}
