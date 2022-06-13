package woowacourse.shoppingcart.dto;

public class CartSaveRequest {

    private Long productId;
    private Integer quantity = 1;

    private CartSaveRequest() {
    }

    public CartSaveRequest(final Long productId, final Integer quantity) {
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
