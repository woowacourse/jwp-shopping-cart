package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class BasicPassword {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$";

    private final String value;

    public BasicPassword(String value) {
        validatePassword(value);
        this.value = value;
    }

    public void validatePassword(String password) {
        if (password.isBlank() || !Pattern.matches(PASSWORD_REGEX, password)) {
            throw new IllegalArgumentException("[ERROR] 패스워드 기본 형식에 어긋납니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
