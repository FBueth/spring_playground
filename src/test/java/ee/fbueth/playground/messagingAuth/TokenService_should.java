package ee.fbueth.playground.messagingAuth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenService_should {

    private TokenService tokenService = new TokenService(new TokenRepository(), new TokenCreator());

    @Test
    void return_a_token_when_generated() {
        //given
        String sender = "123";

        //when
        String token = tokenService.generateToken(sender);

        //then
        assertThat(token).hasSize(5);
    }

    @Test
    void return_token_for_sender_from_repository() {
        //given
        String sender = "123";

        //when
        String token = tokenService.generateToken(sender);

        //then
        assertThat(tokenService.getToken("123")).isEqualTo(token);
    }
}