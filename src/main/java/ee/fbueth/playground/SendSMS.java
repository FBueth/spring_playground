package ee.fbueth.playground;

import org.springframework.stereotype.Component;

@Component
public class SendSMS {
    private TokenReceiver tokenReceiver;
    private SMSSender smsSender;

    public SendSMS(TokenReceiver tokenReceiver, SMSSender smsSender) {
        this.tokenReceiver = tokenReceiver;
        this.smsSender = smsSender;
    }

    void send(SMS sms) {
        String sender = sms.getSender();
        Token token = tokenReceiver.requestToken(sender);
        smsSender.send(sms, token);
    }
}
