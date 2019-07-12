package ee.fbueth.playground;

import org.springframework.stereotype.Component;

@Component
public class Configuration {
    private String tokenUrl = "https://micronaut.herokuapp.com/messaging/auth";
    private String smsUrl = "https://micronaut.herokuapp.com/messaging/send";

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }
}
