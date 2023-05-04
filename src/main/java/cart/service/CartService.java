package cart.service;

import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.Item;
import cart.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    public static final String NOT_EXIST_USER = "존재하지 않는 회원입니다.";
    public static final String NOT_EXIST_ITEM = "존재하지 않는 회원입니다.";

    private final CartDao cartDao;
    private final UserDao userDao;
    private final ItemDao itemDao;

    public CartService(final CartDao cartDao, final UserDao userDao, final ItemDao itemDao) {
        this.cartDao = cartDao;
        this.userDao = userDao;
        this.itemDao = itemDao;
    }

    @Transactional
    public void saveCart(final String email, final Long itemId) {
        User user = userDao.findBy(email).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER));
        Item item = itemDao.findBy(itemId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_ITEM));
        Cart cart = new Cart(user, item);
        cartDao.save(cart);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> loadItemInsideCart(final String email) {
        User user = userDao.findBy(email).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER));
        List<Cart> carts = cartDao.findBy(user.getId());
        return carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCart(final Long cartId) {
        cartDao.delete(cartId);
    }
}
