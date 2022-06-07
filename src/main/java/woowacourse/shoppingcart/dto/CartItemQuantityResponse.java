package woowacourse.shoppingcart.dto;

public class CartItemQuantityResponse {

    private Long id;
    private int quantity;

    public CartItemQuantityResponse() {
    }

    public CartItemQuantityResponse(final Long id, final int quantity) {
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
