package ee.fbueth.playground;

import org.springframework.stereotype.Component;

@Component
public class Configuration {
    private String tokenUrl = "https://micronaut.herokuapp.com/messaging/auth";
    private String smsUrl = "https://micronaut.herokuapp.com/messaging/send";

    String getTokenUrl() {
        return tokenUrl;
    }

    void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    String getSmsUrl() {
        return smsUrl;
    }

    void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }
}
