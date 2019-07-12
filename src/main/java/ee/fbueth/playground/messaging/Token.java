package ee.fbueth.playground.messaging;

public class Token {
    private String tokenValue;

    Token(String userId, String receivedServerToken) {
        this.tokenValue = userId + "_" + receivedServerToken;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
