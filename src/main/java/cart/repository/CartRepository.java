package cart.repository;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import cart.repository.dao.CartDao;
import cart.repository.dao.CartItemDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private final ThreadLocal<List<Item>> caches;
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;

    public CartRepository(CartDao cartDao, CartItemDao cartItemDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        caches = ThreadLocal.withInitial(ArrayList::new);
    }

    public void save(Cart cart) {
        Item item = cart.findDifferentItem(caches.get());
        cartItemDao.insert(cart.getId(), item.getId());

        caches.get()
                .add(item);
    }

    public Cart findCart(User user) {
        Long cartId = cartDao.findByUserId(user.getId());
        List<Item> cartItems = cartItemDao.findAllByCartId(cartId);

        refreshCaches(cartItems);
        return new Cart(cartId, new ArrayList<>(caches.get()));
    }

    private void refreshCaches(final List<Item> cartItems) {
        if (!caches.get().equals(cartItems)) {
            caches.remove();
            caches.set(cartItems);
        }
    }

    public void deleteCartItem(Cart cart) {
        Item deleteItem = cart.findDifferentItem(caches.get());

        cartItemDao.delete(cart.getId(), deleteItem.getId());
        caches.get()
                .remove(deleteItem);
    }
}
