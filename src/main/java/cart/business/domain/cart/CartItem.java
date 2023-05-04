package cart.business.domain.cart;

import cart.business.domain.member.MemberId;
import cart.business.domain.product.ProductId;

import java.util.Objects;

public class CartItem {

    private final CartItemId id;
    private final ProductId productId;
    private final MemberId memberId;

    public CartItem(CartItemId id, ProductId productId, MemberId memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Integer getId() {
        return id.getValue();
    }

    public Integer getProductId() {
        return productId.getValue();
    }

    public Integer getMemberId() {
        return memberId.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id.equals(cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
