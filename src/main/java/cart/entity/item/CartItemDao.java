package cart.entity.item;

import java.util.List;

public interface CartItemDao {

    CartItem save(CartItem cartItem);

    List<CartItem> findAll();

    List<CartItem> findByMemberId(long id);
}
