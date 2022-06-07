package woowacourse.shoppingcart.dto.request;

public class CartItemRequest {
    private Long productId;
    private Integer quantity;

    public CartItemRequest() {
    }

    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItemRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
