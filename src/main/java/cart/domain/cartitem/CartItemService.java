package cart.domain.cartitem;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemDao cartItemDao;

    public CartItemService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public List<CartItem> findAllByMemberId(Long memberId) {
        return cartItemDao.findByMemberId(memberId);
    }
}
