package woowacourse.shoppingcart.dto;

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
}
