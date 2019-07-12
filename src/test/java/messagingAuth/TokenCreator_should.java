package messagingAuth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenCreator_should {
    @Test
    void return_42_when_no_input_provided() {
        //given
        TokenCreator tokenCreator = new TokenCreator();

        //when
        String token = tokenCreator.create();

        //then
        assertThat(token).isEqualTo("42");
    }
}