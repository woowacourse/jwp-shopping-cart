package cart.domain;

import java.util.Objects;

public class Id {
    private final long value;

    public Id(final long id) {
        this.value = id;
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
        final Id id1 = (Id) o;
        return value == id1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
