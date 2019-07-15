package ee.fbueth.playground.messagingAuth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenRepository_should {

    @Test
    void save_sender_and_generated_token_in_a_map() {
        //given
        String sender = "abc";
        String token = "123";
        TokenRepository tokenRepository = new TokenRepository();

        //when
        tokenRepository.save(sender, token);

        //then
        assertThat(tokenRepository.getToken("abc")).isEqualTo(token);
    }
}