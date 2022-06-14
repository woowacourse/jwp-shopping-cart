package woowacourse.shoppingcart.dto;

public class CartRequest {

    private Long productId;
    private Integer quantity;

    private CartRequest() {

    }

    public CartRequest(Long productId, Integer quantity) {
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
