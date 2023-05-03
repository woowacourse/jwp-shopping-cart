package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.user.User;
import cart.entiy.cart.CartEntity;
import cart.entiy.cart.CartProductEntity;
import cart.entiy.user.UserEntityId;
import cart.repository.cart.dao.CartDao;
import cart.repository.cart.dao.CartProductDao;
import cart.repository.cart.mapper.CartMapper;
import cart.repository.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartRepository implements CartRepository {

    private final CartDao cartDao;
    private final CartProductDao cartProductDao;
    private final ProductRepository productRepository;

    public H2CartRepository(final JdbcTemplate jdbcTemplate,
            final ProductRepository productRepository) {
        cartDao = new CartDao(jdbcTemplate);
        cartProductDao = new CartProductDao(jdbcTemplate);
        this.productRepository = productRepository;
    }

    @Override
    public Cart save(final Cart cart) {
        final CartEntity cartEntity = CartEntity.from(cart);
        final CartEntity savedCartEntity = cartDao.save(cartEntity);

        saveCartProducts(cart, savedCartEntity);
        return findCart(cart.getUser(), savedCartEntity);
    }

    @Override
    public Optional<Cart> findByUser(final User user) {
        final CartEntity cartEntity = cartDao.findByUserId(UserEntityId.from(user.getUserId()));
        if (cartEntity == null) {
            return Optional.empty();
        }
        return Optional.of(findCart(user, cartEntity));
    }

    @Override
    public Cart update(final Cart cart) {
        final CartEntity cartEntity = CartEntity.from(cart);

        saveCartProducts(cart, cartEntity);

        return findCart(cart.getUser(), cartEntity);
    }

    private void saveCartProducts(final Cart cart, final CartEntity cartEntity) {
        cartProductDao.deleteAllByCartId(cartEntity.getCartId());
        final List<CartProduct> cartProducts = cart.getCartProducts().getCartProducts();
        final List<CartProductEntity> cartProductEntities = CartMapper.toCartProductEntities(cartProducts,
                cartEntity.getCartId());
        cartProductDao.insertAll(cartProductEntities);
    }

    private Cart findCart(final User user, final CartEntity cartEntity) {
        final List<CartProductEntity> updatedCartProductEntities = cartProductDao.findAllByCartId(
                cartEntity.getCartId());
        return CartMapper.toCart(cartEntity, updatedCartProductEntities, user,
                productRepository.find());
    }
}
