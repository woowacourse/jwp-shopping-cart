package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Password {
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z\\d!@#$%^*]+$");

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private final String password;

    public Password(String password) {
        validatePassword(password);
        this.password = password;
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    private void validatePassword(String password) {
        if (password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 공백일 수 없습니다.");
        }
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 알파벳, 숫자, 일부 특수문자만 들어갈 수 있습니다.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하여야 합니다.");
        }
    }

    public String getPassword() {
        return password;
    }
}
