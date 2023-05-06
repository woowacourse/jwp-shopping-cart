package cart.repository;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import cart.exception.cart.CartNotFoundException;
import cart.repository.dao.CartDao;
import cart.repository.dao.CartItemDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private static final int CORRECT_ROW_COUNT = 1;

    private final CartDao cartDao;
    private final CartItemDao cartItemDao;

    public CartRepository(CartDao cartDao, CartItemDao cartItemDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
    }

    public void saveItem(Cart cart, Item item) {
        cartItemDao.insert(cart.getId(), item.getId());
    }

    public Cart findCart(User user) {
        Long cartId = cartDao.findByEmail(user.getEmail())
                .orElseGet(() -> cartDao.insert(user.getEmail()));
        List<Item> cartItems = cartItemDao.findAllByCartId(cartId);

        return new Cart(cartId, cartItems);
    }

    public void deleteCartItem(Cart cart, Item item) {
        int delete = cartItemDao.delete(cart.getId(), item.getId());

        if (delete != CORRECT_ROW_COUNT) {
            throw new CartNotFoundException("장바구니에 존재하지 않는 상품입니다.");
        }
    }
}
