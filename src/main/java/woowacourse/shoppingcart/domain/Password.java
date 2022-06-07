package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 15;

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateLength(value);
        validateForm(value);
    }

    private void validateLength(String value) {
        if (value.length() >= MIN_PASSWORD_LENGTH || value.length() <= MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상 15자 이하여야 합니다.");
        }
    }

    private void validateForm(String value) {
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
