package cart.persistence;


import cart.domain.Cart.Item;

import java.util.List;

public interface CartDao {

    Long createItem(Long memberId, Long productId);

    Item findItemById(Long id);

    List<Long> findAllItemIds(Long memberId);

    List<Item> findAllItems(Long memberId);

    void deleteItemById(Long id);
}
