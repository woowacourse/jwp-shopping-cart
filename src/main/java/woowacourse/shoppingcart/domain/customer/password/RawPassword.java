package woowacourse.shoppingcart.domain.customer.password;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawPassword {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$");

    private String value;

    public RawPassword(String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(String value) {
        checkNull(value);
        checkFormat(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("비밀번호는 필수 입력 사항입니다.");
        }
    }

    private void checkFormat(String value) {
        Matcher matcher = PASSWORD_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다. "
                    + "(영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용, 8자 이상 16자 이내)");
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
        RawPassword password = (RawPassword)o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
