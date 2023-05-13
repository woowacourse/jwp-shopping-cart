package cart.dao.cart;

import cart.domain.cart.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    void insert(Cart cart);

    List<Cart> findAll();

    Optional<Cart> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
