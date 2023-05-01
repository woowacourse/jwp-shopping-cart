package cart.domain.cart;

import cart.domain.user.User;
import cart.domain.user.UserDao;
import cart.web.controller.user.dto.UserRequest;
import cart.web.exception.ErrorCode;
import cart.web.exception.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private final UserDao userDao;
    private final CartDao cartDao;

    public CartService(final UserDao userDao, final CartDao cartDao) {
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    @Transactional
    public void add(final UserRequest userRequest, final Long productId) {
        final Optional<User> userOptional = userDao.findUserByEmail(userRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }
        final Long insert = cartDao.insert(userOptional.get(), productId);
        System.out.println("insert = " + insert);
    }
}
