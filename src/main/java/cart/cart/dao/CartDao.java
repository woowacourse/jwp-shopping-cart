package cart.cart.dao;

import cart.cart.entity.CartEntity;

public interface CartDao {

    CartEntity insert(int memberId, int productId);

    CartEntity findById(int id);
}
