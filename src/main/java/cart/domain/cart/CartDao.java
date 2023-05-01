package cart.domain.cart;

import cart.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao {

    Long insert(User user, Long productId);
}
