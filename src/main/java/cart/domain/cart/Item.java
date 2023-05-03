package cart.domain.cart;

import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

public class Item {

    private final CartId id;
    private final MemberId userId;
    private final ProductId productId;

    public Item(final long id, final long userId, final long productId) {
        this.id = new CartId(id);
        this.userId = new MemberId(userId);
        this.productId = new ProductId(productId);
    }

    public Item(final long userId, final long productId) {
        this.id = null;
        this.userId = new MemberId(userId);
        this.productId = new ProductId(productId);
    }

    public Item(final long id, final Item item) {
        this.id = new CartId(id);
        this.userId = new MemberId(item.getUserId());
        this.productId = new ProductId(item.getProductId());
    }

    public long getId() {
        return id.getValue();
    }

    public long getUserId() {
        return userId.getValue();
    }

    public long getProductId() {
        return productId.getValue();
    }
}
