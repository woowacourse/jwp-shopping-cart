package cart.service;

import cart.auth.UserInfo;
import cart.dao.UserDao;
import cart.dto.ProductResponse;
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

    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> findAllUsers() {
        return userDao.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void addProductToCart(final UserInfo userInfo, final Long productId) {
        final User user = getUser(userInfo);

        try {
            userDao.addProductToCart(user.getId(), productId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    private User getUser(final UserInfo userInfo) {
        final User user = userDao.findByEmail(userInfo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(INVALID_USER_INFO));


        if (userInfo.isCorrect(user)) {
            return user;
        }

        throw new IllegalArgumentException(INVALID_USER_INFO);
    }

    @Transactional
    public void removeProductInCart(final UserInfo userInfo, final Long userProductId) {
        final User user = getUser(userInfo);

        userDao.deleteProductInCart(user.getId(), userProductId);
    }

    public List<ProductResponse> getAllProductsInCart(final UserInfo userInfo) {
        final User user = getUser(userInfo);

        return userDao.findAllProductsInCart(user.getId()).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
