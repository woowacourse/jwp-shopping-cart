package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.ProductId;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Item item = (Item) o;
        return Objects.equals(memberId, item.memberId) && Objects.equals(productId, item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
