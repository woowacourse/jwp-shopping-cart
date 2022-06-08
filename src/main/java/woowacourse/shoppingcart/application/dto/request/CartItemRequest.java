package woowacourse.shoppingcart.application.dto.request;

public class CartItemRequest {

    private final Long id;
    private final int quantity;

    public CartItemRequest(final Long id, final int quantity) {
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
