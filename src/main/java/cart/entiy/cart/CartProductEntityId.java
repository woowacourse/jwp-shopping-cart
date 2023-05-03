package cart.entiy.cart;

import cart.domain.cart.CartProductId;
import java.util.Objects;

public class CartProductEntityId {

    private final Long value;

    public CartProductEntityId(final Long value) {
        this.value = value;
    }

    public static CartProductEntityId from(final CartProductId cartProductId) {
        return new CartProductEntityId(cartProductId.getValue());
    }

    public CartProductId toDomain() {
        return new CartProductId(value);
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
        final CartProductEntityId that = (CartProductEntityId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
