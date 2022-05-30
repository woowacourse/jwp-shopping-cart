package woowacourse.member.domain;

import woowacourse.member.exception.InvalidPasswordException;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern casePattern = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])");
    private static final Pattern specialCharacterPattern = Pattern.compile("(?=.*?[!@?-])");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = PasswordEncoder.encrypt(value);
    }

    public String getValue() {
        return value;
    }

    private void validate(String value) {
        validateLength(value);
        validateCase(value);
        validateContainsSpecialCharacters(value);
    }

    private void validateLength(String value) {
        if (value.length() < 6) {
            throw new InvalidPasswordException("비밀번호는 6글자 이상이어야 합니다.");
        }
    }

    private void validateCase(String value) {
        if (!casePattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 대소문자를 포함해야 합니다.");
        }
    }

    private void validateContainsSpecialCharacters(String value) {
        if (!specialCharacterPattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
        }
    }
}
