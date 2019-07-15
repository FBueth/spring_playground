package messagingAuth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenCreator_should {

    @Test
    void return_string_of_length_5() {
        //given
        TokenCreator tokenCreator = new TokenCreator();

        //when
        String token = tokenCreator.create();

        //then
        assertThat(token).hasSize(5);
    }
}