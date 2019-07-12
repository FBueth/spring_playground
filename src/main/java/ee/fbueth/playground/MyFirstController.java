package ee.fbueth.playground;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyFirstController {

    private TokenReceiver tokenReceiver;
    private SMSSender smsSender;

    public MyFirstController(TokenReceiver tokenReceiver, SMSSender smsSender) {
        this.tokenReceiver = tokenReceiver;
        this.smsSender = smsSender;
    }

    @GetMapping("/greeting")
    public String greetUser(@RequestParam(value = "name", required = false) String name) {
        if (name == null) {
            return "Hello there";
        }
        return "Hello " + name;
    }

    @PostMapping("/send")
    public ResponseEntity<String> greet(
            @RequestBody SMSDTO smsdto
    ) {
        SMS sms = new SMS.SMSBuilder().from(smsdto.getSenderNumber()).withName(smsdto.getSenderName()).to(smsdto.getReceiver()).withText(smsdto.getText()).build();
        String sender = sms.getSender();
        Token token = tokenReceiver.requestToken(sender);
        smsSender.send(sms, token);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
