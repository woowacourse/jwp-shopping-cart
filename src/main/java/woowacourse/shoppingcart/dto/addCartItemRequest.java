package woowacourse.shoppingcart.dto;

public class addCartItemRequest {

    private Long productId;
    private int quantity;

    public addCartItemRequest() {
    }

    public addCartItemRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
