package cart.dao.cart;

import cart.entity.cart.Cart;
import cart.entity.member.Member;
import java.util.Optional;

public interface CartDao {

    public Long insertCart(Cart cart);

    public Optional<Cart> findByMemberIdAndProductId(Member member, Long productId);
}
