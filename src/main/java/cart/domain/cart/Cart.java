package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

public class Cart {
    
    private final Long id;
    private final long memberId;
    private final long productId;

    public Cart(final long memberId, final long productId) {
        this(null, memberId, productId);
    }

    public Cart(final Long id, final long memberId, final long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static Cart of(final Member member, final Product product) {
        return new Cart(member.getId(), product.getId());
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }
}
