package cart.domain.cart;

import java.util.Objects;

public class CartId {

    private final Long id;

    public CartId() {
        this(null);
    }

    public CartId(final Long id) {
        this.id = id;
    }

    public Long getValue() {
        return id;
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
        return Objects.equals(id, cartId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
