package woowacourse.shoppingcart.application.dto;

public class OrderServiceRequest {

    private final long cartId;

    public OrderServiceRequest(long cartId) {
        this.cartId = cartId;
    }

    public long getCartId() {
        return cartId;
    }
}
