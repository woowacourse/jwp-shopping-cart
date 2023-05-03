package cart.entity.vo;

public class Email {
    private final String stringValue;

    public Email(String stringValue) {
        this.stringValue = stringValue;
    }

    public String value() {
        return stringValue;
    }
}
