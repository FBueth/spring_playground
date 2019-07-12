package ee.fbueth.playground.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "url")
public class MyAppConfig {
    private String tokenurl;
    private String smsurl;

    public String getTokenUrl() {
        return tokenurl;
    }

    public void setTokenUrl(String tokenurl) {
        this.tokenurl = tokenurl;
    }

    public String getSmsUrl() {
        return smsurl;
    }

    public void setSmsUrl(String smsurl) {
        this.smsurl = smsurl;
    }
}
