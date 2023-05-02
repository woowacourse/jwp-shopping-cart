package cart.dao;

import cart.entity.CartEntity;

public interface CartDao {

  Long save(CartEntity cartEntity);
}
