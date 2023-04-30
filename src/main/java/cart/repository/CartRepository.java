package cart.repository;

import cart.entity.Cart;
import cart.repository.exception.CartPersistanceFailedException;

import java.util.List;

public interface CartRepository {
    Cart save(Cart cart) throws CartPersistanceFailedException;

    List<Cart> findAllByMemberEmail(String memberEmail);

    void deleteByMemberEmailAndProductId(String memberEmail, Long productId) throws CartPersistanceFailedException;
}
