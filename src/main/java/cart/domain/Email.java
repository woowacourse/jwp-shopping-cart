package cart.domain;

import cart.domain.exception.WrongEmailFormatException;
import cart.domain.exception.WrongEmailFormatException.Language;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9_]+@[a-z_]+\\.[a-z]{2,}$");

    private final String email;

    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (EMAIL_PATTERN.matcher(email).matches()) {
            return;
        }
        throw new WrongEmailFormatException(Language.KO);
    }

    public String getEmail() {
        return email;
    }
}
