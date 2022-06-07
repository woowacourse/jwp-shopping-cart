package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final int quantity;

    public CartItem(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
