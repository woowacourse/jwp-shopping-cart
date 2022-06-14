package woowacourse.shoppingcart.dto;

public class CartResponse {

    private Long productId;
    private Integer quantity;

    public CartResponse() {
    }

    public CartResponse(Long productId, Integer quantity) {
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
