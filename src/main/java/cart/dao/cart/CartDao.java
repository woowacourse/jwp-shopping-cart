package cart.dao.cart;

import cart.domain.Cart;

import java.util.List;

public interface CartDao {

    List<Cart> findAll(Long memberId);

    void save(Long memberId, Long productId);

    void delete(Long memberId, Long productId);
}

