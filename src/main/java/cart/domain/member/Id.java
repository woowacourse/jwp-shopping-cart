package cart.domain.member;

import java.util.Objects;

public class Id {

    private final long value;

    public Id(final long value) {
        this.value = value;
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
        final Id id = (Id) o;
        return value == id.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
