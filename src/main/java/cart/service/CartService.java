package cart.service;

import cart.controller.auth.dto.AuthInfo;
import cart.controller.dto.CartRequest;
import cart.controller.dto.CartResponse;
import cart.dao.CartDao;
import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.CartData;
import cart.domain.User;
import cart.exception.NotFoundResultException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final UserDao userDao;

    public CartService(CartDao cartDao, UserDao userDao) {
        this.cartDao = cartDao;
        this.userDao = userDao;
    }

    public Long saveCart(final AuthInfo authInfo, final CartRequest cartRequest) {
        Long userId = findIdByEmail(authInfo);
        Cart cart = new Cart.Builder()
                .userId(userId)
                .itemId(cartRequest.getItemId())
                .build();
        return cartDao.save(cart);
    }

    public List<CartResponse> loadAllCart(final AuthInfo authInfo) {
        Long userId = findIdByEmail(authInfo);
        List<CartData> allCart = cartDao.findAll(userId);
        return allCart.stream()
                      .map(CartResponse::from)
                      .collect(Collectors.toList());
    }

    private Long findIdByEmail(final AuthInfo authInfo) {
        Optional<User> findUser = userDao.findByEmail(authInfo.getEmail());
        User user = findUser.orElseThrow(() -> new NotFoundResultException("존재하지 않는 사용자 입니다."));
        return user.getId();
    }
}
