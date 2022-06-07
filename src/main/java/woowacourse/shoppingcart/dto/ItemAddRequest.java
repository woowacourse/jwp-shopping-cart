package woowacourse.shoppingcart.dto;

public class ItemAddRequest {
    private Long productId;

    public ItemAddRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
