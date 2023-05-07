package cart.domain.cart;

import cart.domain.user.User;

import java.util.List;

public interface CartRepository {

    List<CartProduct> findAllByUser1(User user);

    List<CartProduct> findAllByUser2(User user);

    Long insert(Long userId, Long productId);

    void delete(Long cartProductId);
}
