package cart.domain.cart;

import cart.domain.user.User;

import java.util.List;

public interface CartRepository {

    Long insert(User user, Long productId);

    List<CartProduct> findAllByUser(User user);

    void delete(User user, Long cartProductId);
}
