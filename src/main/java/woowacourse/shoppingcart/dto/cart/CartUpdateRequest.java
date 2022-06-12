package woowacourse.shoppingcart.dto.cart;

public class CartUpdateRequest {

    private Long productId;
    private int quantity;

    private CartUpdateRequest() {
    }

    public CartUpdateRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
