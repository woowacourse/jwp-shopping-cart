package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.exception.badRequest.EmailFormattingException;

public class Email {

    private static final Pattern PATTERN = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private final String email;

    public Email(String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new EmailFormattingException();
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
