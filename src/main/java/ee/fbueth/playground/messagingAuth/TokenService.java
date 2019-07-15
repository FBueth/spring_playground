package ee.fbueth.playground.messagingAuth;

import org.springframework.stereotype.Component;

@Component
public class TokenService {

    private TokenRepository tokenRepository;
    private TokenCreator tokenCreator;

    public TokenService(TokenRepository tokenRepository, TokenCreator tokenCreator) {
        this.tokenRepository = tokenRepository;
        this.tokenCreator = tokenCreator;
    }

    public String generateToken(String sender) {
        String token = tokenCreator.create();
        tokenRepository.save(sender, token);
        return token;
    }

    public String getToken(String sender) {
        return tokenRepository.findToken(sender);
    }
}
