package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

public class Item {

    private final MemberId userId;
    private final ProductId productId;

    public Item(final long userId, final long productId) {
        this.userId = new MemberId(userId);
        this.productId = new ProductId(productId);
    }

    public long getUserId() {
        return userId.getValue();
    }

    public long getProductId() {
        return productId.getValue();
    }
}
