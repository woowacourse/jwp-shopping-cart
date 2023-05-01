package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

public class Cart {

    private final Long id;
    private final Long memberId;
    private final Long productId;

    public Cart(final Long id, final Long memberId, final Long productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public static Cart from(final Long id, final Member member, final Product product) {
        return new Cart(id, member.getId(), product.getId());
    }

    public static Cart from(final Member member, final Product product) {
        return new Cart(null, member.getId(), product.getId());
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return this.productId;
    }
}
