package cart.dao.user;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.UserEmail;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CartUserRepositoryImpl implements CartUserRepository {

    private final CartUserDao cartUserDao;

    public CartUserRepositoryImpl(final CartUserDao cartUserDao) {
        this.cartUserDao = cartUserDao;
    }

    @Override
    public Optional<CartUser> findByEmail(final String email) {
        if (isNotExistCartUser(email)) {
            return Optional.empty();
        }

        final CartUserEntity cartUserEntity = cartUserDao.findByEmail(email);

        return Optional.of(toCartUser(cartUserEntity));
    }

    @Override
    public Long save(final CartUser cartUser) {
        final CartUserEntity cartUserEntity = new CartUserEntity(
                cartUser.getUserEmail(),
                cartUser.getPassword()
        );

        return cartUserDao.insert(cartUserEntity);
    }

    @Override
    public List<CartUser> findAll() {
        final List<CartUserEntity> allCartEntities = cartUserDao.findAll();

        return allCartEntities.stream()
                .map(this::toCartUser)
                .collect(Collectors.toList());
    }

    private CartUser toCartUser(final CartUserEntity cartUserEntity) {
        return new CartUser(
                UserEmail.from(cartUserEntity.getEmail()),
                cartUserEntity.getCartPassword()
        );
    }

    private boolean isNotExistCartUser(final String email) {
        final int count = cartUserDao.countByEmail(email);

        if (count < 1) {
            return true;
        }
        return false;
    }
}
