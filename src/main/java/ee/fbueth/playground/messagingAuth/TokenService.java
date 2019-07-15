package ee.fbueth.playground.messagingAuth;

public class TokenService {

    private TokenRepository tokenRepository = new TokenRepository();
    private TokenCreator tokenCreator = new TokenCreator();

    public String generateToken(String sender) {
        String token = tokenCreator.create();
        tokenRepository.save(sender, token);
        return token;
    }
}
