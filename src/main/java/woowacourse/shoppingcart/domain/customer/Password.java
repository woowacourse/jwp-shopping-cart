package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.util.EncryptionUtil;

public class Password {
    private static final Pattern PATTERN = Pattern.compile(
            "^.*(?=^.{8,12}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password fromPlainInput(String input) {
        validateInput(input);
        String encryptedPassword = EncryptionUtil.encrypt(input);
        return new Password(encryptedPassword);
    }

    public static Password fromEncryptedInput(String input) {
        return new Password(input);
    }

    private static void validateInput(String input) {
        if (!PATTERN.matcher(input).matches()) {
            throw new InputFormatException("비밀번호 규약이 맞지 않습니다", ErrorResponse.INVALID_PASSWORD);
        }
    }

    public boolean isSame(String plainText) {
        return EncryptionUtil.isSameEncryptedPassword(value, plainText);
    }

    public String getValue() {
        return value;
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
        return Objects.equals(value, password1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
