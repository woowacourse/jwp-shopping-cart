package cart.service.cart;

import cart.service.cart.domain.CartItems;

import java.util.Optional;

public interface CartDao {

    void deleteCartItem(long cartId);

    Optional<Long> findOneCartItem(long memberId, long productId);

    Long addCartItem(long productId, long memberId);

    CartItems findCartItemsByMemberId(long memberId);
}
