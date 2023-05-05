package cart.entity.vo;

public class Password {
    private final String stringValue;

    public Password(String stringValue) {
        this.stringValue = stringValue;
    }

    public String value() {
        return stringValue;
    }

    @Override
    public boolean equals(Object obj) {
        return this.stringValue.equals((String) obj);
    }
}
