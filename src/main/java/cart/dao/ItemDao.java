package cart.dao;

import cart.domain.Item;

import java.util.List;

public interface ItemDao {
    void save(Item item);

    List<Item> findAll();

    void update(Long id, Item item);

    void delete(Long id);
}
