package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;

import java.util.List;

public interface ItemDao {
    void save(CreateItem createItem);

    List<Item> findAll();

    void update(Long id, CreateItem createItem);

    void delete(Long id);
}
