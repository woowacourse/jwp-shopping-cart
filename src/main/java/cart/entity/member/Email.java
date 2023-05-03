package cart.entity.member;

import cart.exception.common.NullOrBlankException;
import cart.exception.member.InvalidEmailException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String email;

    public Email(final String email) {
        validateNullOrBlank(email);
        validateEmailForm(email);
        this.email = email;
    }

    private void validateNullOrBlank(String email) {
        if (email == null || email.isBlank()) {
            throw new NullOrBlankException();
        }
    }

    private void validateEmailForm(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException();
        }
    }
}
