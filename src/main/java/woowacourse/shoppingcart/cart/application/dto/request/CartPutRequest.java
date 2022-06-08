package woowacourse.shoppingcart.cart.application.dto.request;

public class CartPutRequest {

    private Long quantity;

    public CartPutRequest() {
    }

    public CartPutRequest(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
