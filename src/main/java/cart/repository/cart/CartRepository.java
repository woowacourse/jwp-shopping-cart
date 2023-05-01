package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.member.MemberId;

import java.util.Optional;

public interface CartRepository {
    CartId save(final Cart cart);

    Optional<Cart> joinProductsByMemberId(final MemberId memberId);

    CartId deleteByMemberId(final MemberId memberId);
}
