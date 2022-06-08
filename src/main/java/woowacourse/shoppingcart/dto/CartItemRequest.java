package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private Long productId;
    private Integer count;

    public CartItemRequest(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    private CartItemRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }
}
