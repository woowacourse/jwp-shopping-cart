package woowacourse.shoppingcart.dto.cart;

public class CartRequest {

    private final int productId;
    private final int quantity;

    public CartRequest(final int id, final int quantity) {
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
