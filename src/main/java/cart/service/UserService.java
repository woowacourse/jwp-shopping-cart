package cart.service;

import cart.auth.UserInfo;
import cart.dao.CartDao;
import cart.dao.UserDao;
import cart.dto.UserCartResponse;
import cart.dto.UserResponse;
import cart.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private static final String INVALID_USER_INFO = "잘못된 유저 정보입니다.";

    private final UserDao userDao;
    private final CartDao cartDao;

    public UserService(final UserDao userDao, final CartDao cartDao) {
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public List<UserResponse> findAllUsers() {
        return userDao.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Long addProductToCart(final UserInfo userInfo, final Long productId) {
        final User user = getUser(userInfo);

        try {
            return cartDao.addProductToCart(user.getId(), productId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    private User getUser(final UserInfo userInfo) {
        return userDao.findByEmail(userInfo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(INVALID_USER_INFO));
    }

    @Transactional
    public void deleteProductInCart(final UserInfo userInfo, final Long userProductId) {
        final User user = getUser(userInfo);

        cartDao.deleteProductInCart(user.getId(), userProductId);
    }

    public List<UserCartResponse> getAllProductsInCart(final UserInfo userInfo) {
        final User user = getUser(userInfo);

        return cartDao.findCartItemsByUserId(user.getId()).stream()
                .map(UserCartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
