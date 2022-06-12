package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;

public class Password {

    private static final Pattern PATTERN = Pattern.compile("^.*(?=^.{8,12}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");

    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password from(String password) {
        validatePassword(password);
        String encryptedPassword = PasswordEncryptUtil.encrypt(password);
        return new Password(encryptedPassword);
    }

    private static void validatePassword(String password) {
        if (!PATTERN.matcher(password).matches()) {
            throw new InputFormatException("비밀번호 규약이 맞지 않습니다", ErrorResponse.INVALID_PASSWORD);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
