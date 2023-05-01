package cart.domain.cartproduct;

import java.util.Objects;

public class CartProductId {
    private final long id;

    private CartProductId(final long id) {
        this.id = id;
    }

    public static CartProductId from(final long id) {
        return new CartProductId(id);
    }

    public long getId() {
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
        final CartProductId that = (CartProductId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
