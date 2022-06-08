package woowacourse.shoppingcart.dto.cart;

public class CartItemRequest {

    private final int productId;
    private final int quantity;

    public CartItemRequest(int id, int quantity) {
        this.productId = id;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
