package ee.fbueth.playground;

public class SMS {

    private String senderNumber;
    private String senderName;
    private String receiver;
    private String text;

    private SMS(SMSBuilder builder) {
        this.senderNumber = builder.senderNumber;
        this.senderName = builder.senderName;
        this.receiver = builder.receiver;
        this.text = builder.text;
    }

    String toJson() {
        String template = "{\"text\":\"%s\",\"to\":\"%s\",\"from\":%s,\"display_name\":\"%s\"}";
        return String.format(template, this.text, this.receiver, this.senderNumber, this.senderName);
    }

    String getSender() {
        return this.senderNumber;
    }

    public static class SMSBuilder {
        private String senderNumber;
        private String senderName;
        private String receiver;
        private String text;

        public SMSBuilder() {
        }

        public SMSBuilder from(String senderNumber) {
            this.senderNumber = senderNumber;
            return this;
        }

        public SMSBuilder withName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public SMSBuilder to(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public SMSBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public SMS build() {
            return new SMS(this);
        }
    }

}
