package cart.dao.cart;

import cart.entity.ItemEntity;

import java.util.Map;
import java.util.Optional;

public interface CartDao {

    ItemEntity save(String memberEmail, ItemEntity item);

    Optional<Map<ItemEntity, Long>> findAll(String memberEmail);

    void delete(String memberEmail, ItemEntity item);
}
