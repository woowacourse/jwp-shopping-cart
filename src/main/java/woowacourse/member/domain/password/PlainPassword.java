package woowacourse.member.domain.password;

import woowacourse.member.domain.PasswordEncoder;
import woowacourse.member.exception.InvalidPasswordException;

import java.util.regex.Pattern;

public class PlainPassword {

    private static final Pattern CASE_PATTERN = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("(?=.*?[!@?-])");
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    private final String value;

    public PlainPassword(String value) {
        validate(value);
        this.value = value;
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

    public Password encrypt() {
        return new Password(PasswordEncoder.encrypt(value));
    }
}
