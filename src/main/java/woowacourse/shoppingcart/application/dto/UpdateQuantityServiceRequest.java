package woowacourse.shoppingcart.application.dto;

public class UpdateQuantityServiceRequest {

    private final long memberId;
    private final long cartId;
    private final int quantity;

    public UpdateQuantityServiceRequest(long memberId, long cartId, int quantity) {
        this.memberId = memberId;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
