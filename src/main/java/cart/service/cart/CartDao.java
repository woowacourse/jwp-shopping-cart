package cart.service.cart;

import cart.service.cart.domain.Cart;
import cart.service.member.domain.Member;
import cart.service.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface CartDao {
    Long addProduct(Cart cart);

    List<Product> findProductsByUserId(Long userId);

    void deleteCartItem(Long cartId);

    Optional<Long> findOneCartItem(Member member, Long productId);
}
