package cart.service;

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
        // TODO: 5/3/23 존재하는 상품인지 검증?
        Cart cart = new Cart(user.getId(), itemId);
        cartDao.save(cart);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> loadItemInsideCart(final String email) {
        User user = userDao.findBy(email).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER));
        List<Item> allItem = itemDao.findAll();
        Map<Long, Item> allItemById = convertListToMap(allItem);
        List<Cart> carts = cartDao.findBy(user.getId());
        return carts.stream()
                .map(cart -> convertCartToItem(allItemById, cart))
                .collect(Collectors.toList());
    }

    private Map<Long, Item> convertListToMap(final List<Item> allItem) {
        Map<Long, Item> allItemById = new LinkedHashMap<>();
        for (Item item : allItem) {
            allItemById.put(item.getId(), item);
        }
        return allItemById;
    }

    private ItemResponse convertCartToItem(final Map<Long, Item> allItemById, final Cart cart) {
        Item item = allItemById.get(cart.getItemId());
        return ItemResponse.from(item);
    }

    @Transactional
    public void deleteCart(final String email, final Long itemId) {
        User user = userDao.findBy(email).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER));
        // TODO: 5/3/23 존재하는 상품인지 검증?
        Cart cart = new Cart(user.getId(), itemId);
        cartDao.delete(cart);
    }
}
