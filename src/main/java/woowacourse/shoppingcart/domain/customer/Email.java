package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

    private final String value;

    public Email(String value) {
        validateMailForm(value);
        validateLength(value);

        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() < 8 || value.length() > 50) {
            throw new IllegalArgumentException("이메일은 8자 이상 50자 이하여야합니다.");
        }
    }

    private void validateMailForm(String value) {
        Matcher matcher = EMAIL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식을 지켜야합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
