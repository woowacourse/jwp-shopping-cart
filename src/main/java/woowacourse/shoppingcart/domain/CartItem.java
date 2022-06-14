package woowacourse.shoppingcart.domain;

public class CartItem {

    private final long id;
    private final long memberId;
    private final long productId;
    private final int quantity;

    public CartItem(long id, long memberId, long productId, int quantity) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
