package cart.domain.member.validator;

import static java.lang.System.lineSeparator;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGAX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    public void validate(String email) {
        isNullOrBlank(email);
        isMatchWithPattern(email);
    }

    private void isNullOrBlank(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일 입력이 비어있습니다.");
        }
    }

    private void isMatchWithPattern(String email) {
        boolean isValid = Pattern.matches(EMAIL_REGAX, email);
        if (!isValid) {
            throw new IllegalArgumentException("이메일 형식이 잘못되었습니다." + lineSeparator()
                    + "입력된 이메일: " + email);
        }
    }
}
