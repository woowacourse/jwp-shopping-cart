package cart.persistence;


import cart.domain.cart.Item;

import java.util.List;

public interface CartDao {

    Long saveItem(Item item);

    List<Item> findAllItemsByMemberId(Long memberId);

    void deleteItemById(Long id);
}
