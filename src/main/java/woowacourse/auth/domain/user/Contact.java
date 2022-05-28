package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.exception.InvalidContactFormatException;

public class Contact {
    private static final String CONTACT_REGEX = "\\d{8,11}";
    private final String value;

    public Contact(String value) {
        validateContact(value);
        this.value = value;
    }

    private void validateContact(String value) {
        if (!value.matches(CONTACT_REGEX)) {
            throw new InvalidContactFormatException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "value='" + value + '\'' +
                '}';
    }
}
