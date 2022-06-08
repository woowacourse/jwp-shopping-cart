package woowacourse.shoppingcart.dto.cart;

public class CartItemResponse {

    private final Long productId;
    private final int quantity;

    public CartItemResponse(Long id, int quantity) {
        this.productId = id;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
