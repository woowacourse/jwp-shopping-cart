package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

public class Item {

    private final MemberId memberId;
    private final ProductId productId;

    public Item(final long memberId, final long productId) {
        this.memberId = new MemberId(memberId);
        this.productId = new ProductId(productId);
    }

    public long getMemberId() {
        return memberId.getValue();
    }

    public long getProductId() {
        return productId.getValue();
    }
}
