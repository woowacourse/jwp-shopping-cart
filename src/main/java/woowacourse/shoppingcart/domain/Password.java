package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 유효하지 않습니다.");
        }
    }

    public boolean isSamePassword(String password) {
        return value.equals(password);
    }

    public String getValue() {
        return value;
    }
}
