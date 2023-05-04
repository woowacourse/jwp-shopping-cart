package cart.dao;

import cart.entity.Cart;
import cart.entity.PutCart;

import java.util.List;

public interface CartDao {

    void save(PutCart putCart);

    List<Cart> findByMemberId(Long id);
}
