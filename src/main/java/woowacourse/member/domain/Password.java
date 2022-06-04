package woowacourse.member.domain;

import woowacourse.member.exception.InvalidPasswordException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern CASE_PATTERN = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("(?=.*?[!@?-])");
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password withEncrypt(String value) {
        validate(value);
        return new Password(PasswordEncoder.encrypt(value));
    }

    public static Password withoutEncrypt(String value) {
        return new Password(value);
    }

    private static void validate(String value) {
        validateLength(value);
        validateCase(value);
        validateContainsSpecialCharacters(value);
    }

    private static void validateLength(String value) {
        if (value.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("비밀번호는 6글자 이상이어야 합니다.");
        }
    }

    private static void validateCase(String value) {
        if (!CASE_PATTERN.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 대소문자를 포함해야 합니다.");
        }
    }

    private static void validateContainsSpecialCharacters(String value) {
        if (!SPECIAL_CHARACTER_PATTERN.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(getValue(), password.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
