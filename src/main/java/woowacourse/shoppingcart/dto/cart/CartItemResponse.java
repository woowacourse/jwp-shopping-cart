package woowacourse.shoppingcart.dto.cart;

public class CartItemResponse {

    private final Long id;
    private final int quantity;

    public CartItemResponse(Long id, int quantity) {
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
