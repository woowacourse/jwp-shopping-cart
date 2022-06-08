package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private Long productId;
    private int quantity;

    public AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId, int quantity) {
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
