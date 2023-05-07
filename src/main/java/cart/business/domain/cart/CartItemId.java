package cart.business.domain.cart;

import java.util.Objects;

public class CartItemId {

    private final Integer id;

    public CartItemId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemId cartItemId = (CartItemId) o;
        return id.equals(cartItemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
