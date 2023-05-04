package cart.domain.cart;

import cart.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository {

    Long insert(User user, Long productId);

    List<CartProduct> findAllByUser(User user);

    void delete(User user, Long cartProductId);
}
