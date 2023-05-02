package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;
import cart.domain.user.UserDao;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.web.controller.user.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final UserDao userDao;
    private final CartDao cartDao;

    public CartService(final UserDao userDao, final CartDao cartDao) {
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public Long add(final UserRequest userRequest, final Long productId) {
        final Optional<User> userOptional = userDao.findUserByEmail(userRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        return cartDao.insert(userOptional.get(), productId);
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts(final UserRequest userRequest) {
        final Optional<User> userOptional = userDao.findUserByEmail(userRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        final User user = userOptional.get();

        return cartDao.findAllByUser(user);
    }

    public void delete(final UserRequest userRequest, final Long productId) {
        final Optional<User> userOptional = userDao.findUserByEmail(userRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        final User user = userOptional.get();
        cartDao.delete(user, productId);
    }
}
