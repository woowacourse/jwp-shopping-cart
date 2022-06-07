package woowacourse.shoppingcart.application.dto;

public class CartUpdateServiceRequest {

    private final Long productId;
    private final Integer quantity;

    public CartUpdateServiceRequest(final Long productId, final Integer quantity) {
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
