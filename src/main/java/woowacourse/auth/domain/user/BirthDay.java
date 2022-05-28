package woowacourse.auth.domain.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BirthDay {
    private final LocalDate value;

    private BirthDay(LocalDate value) {
        this.value = value;
    }

    public static BirthDay from(String value) {
        return new BirthDay(LocalDate.parse(Objects.requireNonNull(value), DateTimeFormatter.ISO_DATE));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BirthDay birthDay = (BirthDay) o;
        return Objects.equals(value, birthDay.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "BirthDay{" +
                "value='" + value + '\'' +
                '}';
    }
}
