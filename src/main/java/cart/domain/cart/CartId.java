package cart.domain.cart;

public class CartId {

    private final Long id;

    public CartId(final Long id) {
        this.id = id;
    }

    public Long getValue() {
        return id;
    }
}
