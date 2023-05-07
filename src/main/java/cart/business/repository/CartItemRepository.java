package cart.business.repository;

import cart.business.domain.cart.CartItem;

import java.util.List;

public interface CartItemRepository {

    Integer save(CartItem cartItem);

    CartItem remove(Integer cartItemId);

    List<CartItem> findAllByMemberId(Integer memberId);
}
