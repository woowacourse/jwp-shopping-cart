package cart.dao.user;

import cart.domain.user.CartUser;
import cart.domain.user.UserEmail;
import cart.domain.user.UserRepository;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class CartUserRepositoryImpl implements UserRepository {

    private final CartUserDao cartUserDao;

    public CartUserRepositoryImpl(CartUserDao cartUserDao) {
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
