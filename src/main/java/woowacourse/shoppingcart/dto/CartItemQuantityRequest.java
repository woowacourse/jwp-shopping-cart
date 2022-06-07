package woowacourse.shoppingcart.dto;

public class CartItemQuantityRequest {

    private Long id;
    private int quantity;

    public CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(final Long id, final int quantity) {
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
