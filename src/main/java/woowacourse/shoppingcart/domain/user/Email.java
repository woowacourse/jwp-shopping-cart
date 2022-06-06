package woowacourse.shoppingcart.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.badrequest.InvalidEmailException;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private final String email;

    public Email(String email) {
        this.email = email;
        validate();
    }

    private void validate() {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidEmailException();
        }
    }

    public String value() {
        return email;
    }
}
