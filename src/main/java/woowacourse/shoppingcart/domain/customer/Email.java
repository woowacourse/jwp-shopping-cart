package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.exception.InvalidEmailFormatException;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private final String email;

    public Email(final String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(final String email) {
        if (isEmailOutOfForm(email)) {
            throw new InvalidEmailFormatException();
        }
    }

    private boolean isEmailOutOfForm(final String email) {
        return !EMAIL_PATTERN.matcher(email).matches();
    }

    public String getEmail() {
        return email;
    }
}
