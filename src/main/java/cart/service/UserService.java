package cart.service;

import cart.dao.UserDao;
import cart.dto.ProductResponse;
import cart.dto.UserResponse;
import cart.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

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
    public void addProductToCart(final String email, final Long productId) {
        final User user = getUser(email);

        userDao.addProductToCart(user.getId(), productId);
    }

    private User getUser(final String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Transactional
    public void removeProductInCart(final String email, final Long userProductId) {
        final User user = getUser(email);

        userDao.deleteProductInCart(user.getId(), userProductId);
    }

    public List<ProductResponse> getAllProductsInCart(final String email) {
        final User user = getUser(email);

        return userDao.findAllProductsInCart(user.getId()).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
