package woowacourse.shoppingcart.ui.dto;

public class OrderDetailRequest {

    private Long cartId;
    private int quantity;

    private OrderDetailRequest() {
    }

    public OrderDetailRequest(Long cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
