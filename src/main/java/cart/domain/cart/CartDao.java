package cart.domain.cart;

import cart.domain.Product;
import cart.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface CartDao {
    Long addProduct(Cart cart);

    List<Product> findProductsByUserId(Long userId);

    void deleteCartItem(Long cartId);

    Optional<Long> findOneCartItem(Member member, Long productId);
}
