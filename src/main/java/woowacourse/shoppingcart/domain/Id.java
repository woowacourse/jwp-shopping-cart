package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.invalid.InvalidIdException;

public class Id {

    private static final int MIN = 1;

    private final Long value;

    public Id(final Long id) {
        validateRange(id);
        this.value = id;
    }

    private void validateRange(final Long id) {
        if (id != null && id < MIN) {
            throw new InvalidIdException();
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
