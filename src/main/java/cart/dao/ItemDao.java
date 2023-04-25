package cart.dao;

import cart.entity.Item;

import java.util.List;

public interface ItemDao {
    void save(Item item);

    List<Item> findAll();

    void update(Item item);

    void delete(Long id);
}
