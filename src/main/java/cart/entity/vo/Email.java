package cart.entity.vo;

import java.util.regex.Pattern;

public class Email {
    private final String stringValue;
    private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public Email(String stringValue) {
        validate(stringValue);
        this.stringValue = stringValue;
    }

    public String value() {
        return stringValue;
    }

    private void validate(final String string) {
        if (!pattern.matcher(string).matches()) {
            throw new IllegalArgumentException("잘못된 email 형식입니다");
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this.stringValue.equals((String) obj);
    }
}
