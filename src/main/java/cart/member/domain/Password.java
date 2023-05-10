package cart.member.domain;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Password {

    static final int MIN_LENGTH = 8;
    static final int MAX_LENGTH = 40;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");

    private final String password;

    public Password(String password) {
        final String stripped = password.strip();
        validateLengthInRange(stripped);
        validatePasswordPattern(stripped);
        this.password = stripped;
    }

    private void validateLengthInRange(String password) {
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("비밀 번호는 8 ~ 40자로 구성됩니다.");
        }
    }

    private void validatePasswordPattern(String password) {
        final Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀 번호는 오직 숫자와 영어로 구성됩니다.");
        }
    }
}

