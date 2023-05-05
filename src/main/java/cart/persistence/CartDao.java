package cart.persistence;


import cart.domain.cart.Item;

import java.util.List;

public interface CartDao {

    Long createItem(Long memberId, Long productId);

    Item findItemById(Long id);

    List<Item> findAllItemsByMemberId(Long memberId);

    void deleteItemById(Long id);
}
