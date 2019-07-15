package ee.fbueth.playground.messagingAuth;

import java.util.HashMap;
import java.util.Map;

public class TokenRepository {

    private Map<String, String> generatedTokens = new HashMap<>();

    public void save(String sender, String token) {
        generatedTokens.put(sender, token);
    }

    public String getToken(String sender) {
        return generatedTokens.get(sender);
    }
}
