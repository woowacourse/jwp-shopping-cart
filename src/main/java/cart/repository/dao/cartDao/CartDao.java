package cart.repository.dao.cartDao;

import cart.dto.CartItemDto;
import cart.entity.Cart;

import java.util.List;

public interface CartDao {

    Long save(final Cart cart);

    List<CartItemDto> findAllCartItemsByMemberId(final Long memberId);

    int deleteByCartId(final Long cartId);
}
