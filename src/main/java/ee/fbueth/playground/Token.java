package ee.fbueth.playground;

public class Token {
    private String tokenValue;

    Token(String userId, String receivedServerToken) {
        this.tokenValue = userId + "_" + receivedServerToken;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
