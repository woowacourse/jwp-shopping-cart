package cart.entity.item;

import java.util.List;

public interface CartItemDao {

    CartItem save(CartItem cartItem);

    List<CartItem> findByMemberId(long id);

    void delete(long memberId, long productId);
}
