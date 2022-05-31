package woowacourse.shoppingcart.domain.customer.privacy;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidContactFormatException;

public class Contact {
    private static final String CONTACT_REGEX = "\\d{8,11}";
    private final String value;

    public Contact(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(CONTACT_REGEX)) {
            throw new InvalidContactFormatException();
        }
    }

    public String getValue() {
        return value;
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
