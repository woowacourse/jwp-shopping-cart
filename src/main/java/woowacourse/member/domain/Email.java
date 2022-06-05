package woowacourse.member.domain;

import java.util.regex.Pattern;
import woowacourse.member.exception.EmailNotValidException;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    private final String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(final String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailNotValidException();
        }
    }

    public String getEmail() {
        return email;
    }
}
