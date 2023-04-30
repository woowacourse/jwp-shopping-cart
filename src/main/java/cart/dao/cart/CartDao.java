package cart.dao.cart;

import cart.entity.ItemEntity;

import java.util.List;

public interface CartDao {
    Long save(String memberEmail, Long itemId);

    List<ItemEntity> findAll(String memberEmail);

    void delete(String memberEmail, Long itemId);
}
