package cart.persistence;


import cart.domain.Item;

public interface CartDao {

    Long createItem(Long memberId, Long productId);

    Item findItemById(Long id);
}
