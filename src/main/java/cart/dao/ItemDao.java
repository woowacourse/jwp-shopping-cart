package cart.dao;

import cart.domain.Item;
import cart.entity.ItemEntity;

import java.util.List;

public interface ItemDao {
    void save(Item item);

    List<ItemEntity> findAll();

    void update(Long id, Item item);

    void delete(Long id);
}
