package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Email {

    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    private final String value;

    public Email(String value) {
        validateEmail(value);
        this.value = value;
    }

    private void validateEmail(String email) {
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("[ERROR] 이메일 기본 형식에 어긋납니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
