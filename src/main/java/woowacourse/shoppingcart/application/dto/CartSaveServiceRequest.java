package woowacourse.shoppingcart.application.dto;

public class CartSaveServiceRequest {

    private final Long productId;
    private final Integer quantity;

    public CartSaveServiceRequest(final Long productId, final Integer quantity) {
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
