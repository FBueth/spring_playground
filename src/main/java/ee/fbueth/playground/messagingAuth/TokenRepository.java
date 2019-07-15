package ee.fbueth.playground.messagingAuth;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenRepository {

    private Map<String, String> generatedTokens = new HashMap<>();

    public void save(String sender, String token) {
        generatedTokens.put(sender, token);
    }

    public String findToken(String sender) {
        return generatedTokens.get(sender);
    }
}
