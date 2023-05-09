package cart.domain.cart;

import java.util.Objects;

public class CartId {

    private final long value;

    public CartId(final long id) {
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
        final CartId cartId = (CartId) o;
        return value == cartId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
