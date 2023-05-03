package cart.entiy.cart;

import cart.domain.cart.CartId;
import java.util.Objects;

public class CartEntityId {

    private final Long value;

    public CartEntityId(final Long value) {
        this.value = value;
    }

    public static CartEntityId from(final CartId cartId) {
        return new CartEntityId(cartId.getValue());
    }

    public Long getValue() {
        return value;
    }

    public CartId toDomain() {
        return new CartId(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartEntityId that = (CartEntityId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
