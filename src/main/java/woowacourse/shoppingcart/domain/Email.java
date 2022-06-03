package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidEmailException;

public class Email {

    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    private final String value;

    public Email(String value) {
        validateForm(value);
        validateLength(value);
        this.value = value;
    }

    private void validateForm(String value) {
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            throw new InvalidEmailException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > 64) {
            throw new InvalidEmailException("이메일은 64자 이하입니다.");
        }
    }
}
