package woowacourse.shoppingcart.domain.customer.privacy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Birthday {
    private static final Birthday EMPTY = new Birthday(null);

    private final LocalDate value;

    private Birthday(LocalDate value) {
        this.value = value;
    }

    public static Birthday from(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return EMPTY;
        }
        return new Birthday(LocalDate.parse(value, DateTimeFormatter.ISO_DATE));
    }

    public LocalDate getValue() {
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
        Birthday birthday = (Birthday) o;
        return Objects.equals(value, birthday.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Birthday{" +
                "value='" + value + '\'' +
                '}';
    }
}
