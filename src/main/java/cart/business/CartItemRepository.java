package cart.business;

import cart.business.domain.cart.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository {

    Integer save(CartItem cartItem);

    CartItem remove(Integer cartItemId);

    List<CartItem> findAllById(Integer memberId);
}
