package cart.dao;


import cart.entity.ItemEntity;

import java.util.List;

public interface ItemDao {
    ItemEntity save(ItemEntity item);

    List<ItemEntity> findAll();

    void update(Long id, ItemEntity item);

    void delete(Long id);
}
