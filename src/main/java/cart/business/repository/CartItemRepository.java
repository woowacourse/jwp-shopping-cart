package cart.business.repository;

import cart.business.domain.cart.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository {

    Integer save(CartItem cartItem);

    CartItem remove(Integer cartItemId);

    List<CartItem> findAllByMemberId(Integer memberId);
}
