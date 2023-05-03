package cart.service.cart;

import cart.entity.item.CartItem;
import cart.entity.item.CartItemDao;
import org.springframework.stereotype.Service;

@Service
public class CartItemAddService {
    private final CartItemDao cartItemDao;

    public CartItemAddService(final CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public CartItem addItem(final long memberId, final long productId) {
        final CartItem cartItem = new CartItem(memberId, productId);
        return cartItemDao.save(cartItem);
    }
}
