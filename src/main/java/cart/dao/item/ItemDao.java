package cart.dao.item;


import cart.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface ItemDao {

    ItemEntity save(ItemEntity item);

    Optional<List<ItemEntity>> findAll();

    Optional<ItemEntity> findById(Long id);

    void update(Long id, ItemEntity item);

    void delete(Long id);
}
