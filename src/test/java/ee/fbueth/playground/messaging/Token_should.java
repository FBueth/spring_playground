package ee.fbueth.playground.messaging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Token_should {

    @Test
    void concat_userId_and_received_value_to_one_string() {
        //given
        String userId = "123";
        String receivedValue = "abc";

        //when
        Token token = new Token(userId, receivedValue);

        //then
        assertEquals("123_abc", token.getTokenValue());
    }
}