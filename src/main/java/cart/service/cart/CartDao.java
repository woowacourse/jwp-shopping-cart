package cart.service.cart;

import cart.service.cart.domain.CartItems;
import cart.service.member.domain.Member;
import cart.service.product.domain.Product;

import java.util.Optional;

public interface CartDao {

    void deleteCartItem(Long cartId);

    Optional<Long> findOneCartItem(Member member, Long productId);

    Long addCartItem(Product product, Member member);

    CartItems findCartItemsByMember(Member member);
}
