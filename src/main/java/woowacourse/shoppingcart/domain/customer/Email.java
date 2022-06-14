package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Email {

    private static final String EMAIL_FORMAT = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";

    private final String email;

    public Email(final String email) {
        validateEmail(email);
        this.email = email;
    }

    public static void validateEmail(final String email) {
        if (email.isBlank() || !email.matches(EMAIL_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
    }

    public String getValue() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
