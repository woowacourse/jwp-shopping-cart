package woowacourse.auth.domain;

import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class UserPassword {

    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 6;

    private final String password;

    public UserPassword(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password == null || password.isBlank() || !isValidLength(password)) {
            throw new InvalidPasswordException();
        }
        if (password.matches("[ㄱ-ㅎ]+|[ㅏ-ㅣ]+|[가-힣]+") || !password.matches("\\S+")) {
            throw new InvalidPasswordException();
        }
    }

    private boolean isValidLength(String value) {
        return MIN_LENGTH <= value.length() && value.length() <= MAX_LENGTH;
    }

    public String getPassword() {
        return password;
    }
}
