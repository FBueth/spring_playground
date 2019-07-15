package ee.fbueth.playground.messagingAuth;

import java.util.Random;
import java.util.stream.Collectors;

public class TokenCreator {

    public String create() {
        int length = 5;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        String str = new Random()
                .ints(length, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());
        return str;
    }
}
