package cart.service;

import cart.controller.dto.auth.AuthInfo;
import cart.controller.dto.response.CartResponse;
import cart.dao.CartDao;
import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.CartData;
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

    public CartService(CartDao cartDao, UserDao userDao) {
        this.cartDao = cartDao;
        this.userDao = userDao;
    }

    public Long saveCart(final AuthInfo authInfo, final Long itemId) {
        Long userId = findIdByEmail(authInfo);
        Cart cart = new Cart.Builder()
                .userId(userId)
                .itemId(itemId)
                .build();
        return cartDao.save(cart);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> loadAllCart(final AuthInfo authInfo) {
        Long userId = findIdByEmail(authInfo);
        List<CartData> allCart = cartDao.findAll(userId);
        return allCart.stream()
                      .map(CartResponse::from)
                      .collect(Collectors.toList());
    }

    public void deleteItem(final Long cartId) {
        validateExistCart(cartId);
        cartDao.deleteBy(cartId);
    }

    private Long findIdByEmail(final AuthInfo authInfo) {
        Optional<User> findUser = userDao.findByEmail(authInfo.getEmail());
        User user = findUser.orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자 입니다."));
        return user.getId();
    }

    private void validateExistCart(Long cartId) {
        Optional<List<Cart>> findCart = cartDao.findBy(cartId);
        if (findCart.isEmpty()) {
            throw new NotFoundResultException("존재하지 않는 아이템 입니다.");
        }
    }
}
