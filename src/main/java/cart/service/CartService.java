package cart.service;

import cart.controller.dto.auth.AuthInfoDto;
import cart.controller.dto.response.CartResponse;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.CartData;
import cart.domain.Item;
import cart.domain.User;
import cart.exception.NotFoundResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final UserDao userDao;
    private final ItemDao itemDao;

    public CartService(CartDao cartDao, UserDao userDao, ItemDao itemDao) {
        this.cartDao = cartDao;
        this.userDao = userDao;
        this.itemDao = itemDao;
    }

    public Long saveCart(final AuthInfoDto authInfoDto, final Long itemId) {
        Long userId = findUserIdByEmail(authInfoDto);
        validateExistItem(itemId);
        Cart cart = new Cart.Builder()
                .userId(userId)
                .itemId(itemId)
                .build();
        return cartDao.save(cart);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> loadAllCart(final AuthInfoDto authInfoDto) {
        Long userId = findUserIdByEmail(authInfoDto);
        List<CartData> allCart = cartDao.findAll(userId);
        return allCart.stream()
                      .map(CartResponse::from)
                      .collect(Collectors.toList());
    }

    public void deleteItem(final Long cartId) {
        validateExistCart(cartId);
        cartDao.deleteBy(cartId);
    }

    private Long findUserIdByEmail(final AuthInfoDto authInfoDto) {
        Optional<User> findUser = userDao.findByEmail(authInfoDto.getEmail());
        User user = findUser.orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자 입니다."));
        return user.getId();
    }

    private void validateExistItem(final Long itemId) {
        Optional<Item> findItem = itemDao.findById(itemId);
        if (findItem.isEmpty()) {
            throw new NotFoundResultException("존재하지 않는 아이템 입니다.");
        }
    }

    private void validateExistCart(Long cartId) {
        Optional<Cart> findCart = cartDao.findById(cartId);
        if (findCart.isEmpty()) {
            throw new NotFoundResultException("존재하지 않는 장바구니 아이템 입니다.");
        }
    }
}
