package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    CartId saveByMemberId(final MemberId memberId, final ProductId productId);

    Optional<Cart> findByCartId(final CartId cartId);

    List<Cart> findAllByMemberId(final MemberId memberId);

    int deleteByMemberId(final MemberId memberId, final ProductId productId);
}
