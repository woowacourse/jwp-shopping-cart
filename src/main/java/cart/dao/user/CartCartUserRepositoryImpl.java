package cart.dao.user;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.UserEmail;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

        return toCartUser(cartUserEntity);
    }

    @Override
    public Long save(CartUser cartUser) {
        CartUserEntity cartUserEntity = new CartUserEntity(
                null, //FIXME
                cartUser.getUserEmail(),
                cartUser.getPassword()
        );

        return cartUserDao.insert(cartUserEntity);
    }

    @Override
    public List<CartUser> findAll() {
        List<CartUserEntity> allCartEntities = cartUserDao.findAll();

        return allCartEntities.stream()
                .map(this::toCartUser)
                .collect(Collectors.toList());
    }

    private CartUser toCartUser(CartUserEntity cartUserEntity) {
        return new CartUser(
                UserEmail.from(cartUserEntity.getEmail()),
                cartUserEntity.getCartPassword()
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
