package ee.fbueth.playground.messagingAuth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/messaging/auth")
    public ResponseEntity<String> returnToken(@RequestHeader("sender") String sender) {
        String token = tokenService.generateToken(sender);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
