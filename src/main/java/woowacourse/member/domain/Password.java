package woowacourse.member.domain;

import woowacourse.member.exception.InvalidPasswordException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern casePattern = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])");
    private static final Pattern specialCharacterPattern = Pattern.compile("(?=.*?[!@?-])");

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
        if (value.length() < 6) {
            throw new InvalidPasswordException("비밀번호는 6글자 이상이어야 합니다.");
        }
    }

    private static void validateCase(String value) {
        if (!casePattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 대소문자를 포함해야 합니다.");
        }
    }

    private static void validateContainsSpecialCharacters(String value) {
        if (!specialCharacterPattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
        }
    }

    public boolean isSameAs(String input) {
        String comparison = PasswordEncoder.encrypt(input);
        return value.equals(comparison);
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
