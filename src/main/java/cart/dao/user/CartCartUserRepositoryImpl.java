package cart.dao.user;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.UserEmail;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class CartCartUserRepositoryImpl implements CartUserRepository {

    private final CartUserDao cartUserDao;

    public CartCartUserRepositoryImpl(CartUserDao cartUserDao) {
        this.cartUserDao = cartUserDao;
    }

    @Override
    public CartUser findByEmail(String email) {
        CartUserEntity cartUserEntity = validateIsNotEmptyResult(() -> cartUserDao.findByEmail(email));

        return toCarUser(cartUserEntity);
    }

    private CartUser toCarUser(CartUserEntity cartUserEntity) {
        return new CartUser(
                UserEmail.from(cartUserEntity.getEmail()),
                cartUserEntity.getPassword()
        );
    }

    private <T> T validateIsNotEmptyResult(Supplier<T> findQuery) {
        try {
            return findQuery.get();
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }
}
