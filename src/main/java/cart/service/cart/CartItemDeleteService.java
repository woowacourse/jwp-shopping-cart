package cart.service.cart;

import cart.entity.item.CartItemDao;
import org.springframework.stereotype.Service;

@Service
public class CartItemDeleteService {
    private final CartItemDao cartItemDao;

    public CartItemDeleteService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public void deleteItem(final long memberId, final long productId) {
        cartItemDao.delete(memberId, productId);
    }
}
