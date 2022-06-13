package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class Password {

    private final Pattern pattern = Pattern.compile("[ㄱ-ㅎ]+|[ㅏ-ㅣ]+|[가-힣]+");

    private final String value;

    public Password(String value) {
        validateNull(value);
        validateForm(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNull(String value) {
        if (value == null) {
            throw new InvalidPasswordException();
        }
    }

    public void validateForm(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            throw new InvalidPasswordException("비밀번호는 한글이나 공백을 포함할 수 없습니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() < 6) {
            throw new InvalidPasswordException("비밀번호는 6자 이상입니다.");
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
        if (o == null || getClass() != o.getClass()) {
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
