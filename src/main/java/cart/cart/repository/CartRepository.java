package cart.cart.repository;

import cart.cart.domain.Cart;
import cart.cart.domain.CartId;
import cart.member.domain.MemberId;
import cart.product.domain.ProductId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    CartId saveByMemberId(final MemberId memberId, final ProductId productId);

    Optional<Cart> findByCartId(final CartId cartId);

    Optional<Cart> findByMemberIdAndProductId(final MemberId memberId, final ProductId productId);

    List<Cart> findAllByMemberId(final MemberId memberId);

    int deleteByMemberId(final MemberId memberId, final ProductId productId);
}
