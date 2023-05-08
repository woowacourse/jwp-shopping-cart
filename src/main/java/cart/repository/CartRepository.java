package cart.repository;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import cart.repository.dao.CartDao;
import cart.repository.dao.CartItemDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private final CartDao cartDao;
    private final CartItemDao cartItemDao;

    public CartRepository(CartDao cartDao, CartItemDao cartItemDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
    }

    public void save(Cart cart) {
        cartItemDao.deleteByCartId(cart.getId());
        cartItemDao.insertCartItem(cart);
    }

    public Cart findCart(User user) {
        Long cartId = cartDao.findByUserId(user.getId());
        List<Item> cartItems = cartItemDao.findAllByCartId(cartId);

        return new Cart(cartId, cartItems);
    }
}
