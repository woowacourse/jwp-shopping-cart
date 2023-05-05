package cart.dao.cart;

import cart.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    Long save(String memberEmail, Long itemId);

    Optional<List<CartEntity>> findAll(String memberEmail);

    Optional<CartEntity> findByEmailAndId(String memberEmail, Long itemId);

    void update(String memberEmail, CartEntity cart);

    void delete(String memberEmail, Long itemId);
}
