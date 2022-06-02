package woowacourse.member.domain;

import java.util.regex.Pattern;
import woowacourse.member.exception.EmailNotValidException;

public class Email {

    private final String EMAIL_REGEX = "^(.+)@(.+)$";

    private final String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(final String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new EmailNotValidException();
        }
    }

    public String getEmail() {
        return email;
    }
}
