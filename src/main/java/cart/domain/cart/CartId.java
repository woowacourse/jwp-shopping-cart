package cart.domain.cart;

public class CartId {

    private final long value;

    public CartId(final long id) {
        this.value = id;
    }

    public long getValue() {
        return value;
    }
}
