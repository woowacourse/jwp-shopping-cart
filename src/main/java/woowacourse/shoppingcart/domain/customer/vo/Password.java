package woowacourse.shoppingcart.domain.customer.vo;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.common.utils.EncryptAlgorithm;

public class Password {

    private static final Pattern FORMAT = Pattern
            .compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])).*");
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = EncryptAlgorithm.encrypt(value);
    }

    private void validate(String value) {
        validateFormat(value);
        validateLength(value);
    }

    private void validateFormat(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d ~ %d자로 생성 가능합니다.", MIN_LENGTH, MAX_LENGTH));
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
