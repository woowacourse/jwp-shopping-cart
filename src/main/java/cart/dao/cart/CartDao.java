package cart.dao.cart;

import cart.dto.cartitem.CartItem;
import cart.entity.cart.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDao {

    public Long insertCart(Cart cart);

    public Optional<Cart> findByMemberIdAndProductId(Long memberId, Long productId);

    public List<CartItem> findByMemberId(Long memberId);

    public void updateCart(Cart cart);

    public void deleteCart(Long memberId, Long productId);
}
