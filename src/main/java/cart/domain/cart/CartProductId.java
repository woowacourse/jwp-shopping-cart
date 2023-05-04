package cart.domain.cart;

import java.util.Objects;

public class CartProductId {

    private final Long value;

    public CartProductId() {
        this(null);
    }

    public CartProductId(final Long value) {
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
        final CartProductId that = (CartProductId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
