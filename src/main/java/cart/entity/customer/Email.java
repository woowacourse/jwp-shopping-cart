package cart.entity.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Email {

    private static final String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    private final String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value) {
        Pattern patter = Pattern.compile(emailRegex);
        Matcher mater = patter.matcher(value);
        if (mater.matches()) {
            return;
        }
        throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
    }

    public String getValue() {
        return value;
    }
}
