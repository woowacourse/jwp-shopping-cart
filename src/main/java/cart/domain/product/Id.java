package cart.domain.product;

import java.util.Objects;

public class Id {

    private final Long value;

    public Id(final Long value) {
        this.value = value;
    }

    public Long getValue() {
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
        final Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
