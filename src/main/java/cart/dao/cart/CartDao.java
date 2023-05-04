package cart.dao.cart;

import cart.dto.cartitem.CartItem;
import cart.entity.cart.Cart;
import cart.entity.member.Member;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    public Long insertCart(Cart cart);

    public Optional<Cart> findByMemberIdAndProductId(Member member, Long productId);

    public List<CartItem> findByMemberId(Member member);

    public void updateCart(Cart cart);

    public void deleteCart(Member member, Long productId);
}
