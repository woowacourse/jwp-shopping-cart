package cart.cart.repository;

import cart.cart.entity.Cart;

import java.util.List;

public interface CartRepository {
    Cart save(Cart cart);

    List<Cart> findAllByMemberEmail(String memberEmail);

    void deleteByMemberEmailAndProductId(String memberEmail, Long productId);
}
