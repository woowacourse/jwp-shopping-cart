package cart.dao.cart;

import cart.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartDao {
    Long save(String memberEmail, Long itemId);

    Optional<List<ItemEntity>> findAll(String memberEmail);

    void delete(String memberEmail, Long itemId);
}
