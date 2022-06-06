package woowacourse.shoppingcart.dto;

public class CartItemUpdateRequest {
    private Long productId;
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItemUpdateRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
