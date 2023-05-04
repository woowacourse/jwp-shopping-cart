package cart.persistence;


import cart.domain.Item;

import java.util.List;

public interface CartDao {

    Long createItem(Long memberId, Long productId);

    Item findItemById(Long id);

    List<Long> findAllItems(Long memberId);
}
