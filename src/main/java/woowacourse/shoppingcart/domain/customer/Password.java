package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$");

    private final String value;

    public Password(String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(String value) {
        checkNull(value);
        checkLength(value);
        checkFormat(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("비밀번호는 필수 입력 사항입니다.");
        }
    }

    private void checkLength(String value) {
        if (value.length() < 8 || 16 < value.length()) {
            throw new IllegalArgumentException("비밀번호 길이가 올바르지 않습니다. (길이: 8이상 16이하)");
        }
    }

    private void checkFormat(String value) {
        Matcher matcher = PASSWORD_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용)");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Password password = (Password)o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
