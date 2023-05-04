package cart.domain.cart;

import cart.domain.Product;
import cart.domain.member.Member;

import java.util.List;

public interface CartDao {
    Long addProduct(Cart cart);

    List<Product> findProductsByUserId(Long userId);

    int deleteCartItem(Member member, Long productId);
}
