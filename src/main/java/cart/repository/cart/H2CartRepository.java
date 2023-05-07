package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.cart.CartProduct;
import cart.domain.user.UserId;
import cart.entiy.cart.CartEntity;
import cart.entiy.cart.CartProductEntity;
import cart.repository.cart.dao.CartDao;
import cart.repository.cart.dao.CartProductDao;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartRepository implements CartRepository {

    private final CartDao cartDao;
    private final CartProductDao cartProductDao;

    public H2CartRepository(final JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
        cartProductDao = new CartProductDao(jdbcTemplate);
    }

    @Override
    public Cart save(final Cart cart) {
        CartEntity cartEntity = CartEntity.from(cart);
        if (cartEntity.getCartId().getValue() == null) {
            cartEntity = cartDao.save(cartEntity);
        }

        saveCartProducts(cart, cartEntity);
        return findCart(cart.getUserId(), cartEntity);
    }

    private void saveCartProducts(final Cart cart, final CartEntity cartEntity) {
        cartProductDao.deleteAllByCartId(cartEntity.getCartId());
        final List<CartProduct> cartProducts = cart.getCartProducts().getCartProducts();
        final List<CartProductEntity> cartProductEntities = toCartProductEntities(cartProducts,
                cartEntity.getCartId());
        cartProductDao.insertAll(cartProductEntities);
    }

    private List<CartProductEntity> toCartProductEntities(final List<CartProduct> cartProducts,
            final CartId cartEntityId) {
        return cartProducts.stream()
                .map(cartProduct -> toCartProductEntity(cartEntityId, cartProduct))
                .collect(Collectors.toList());
    }

    private CartProductEntity toCartProductEntity(final CartId cartId, final CartProduct cartProduct) {
        return new CartProductEntity(
                cartProduct.getCartProductId(),
                cartId,
                cartProduct.getProductId()
        );
    }

    @Override
    public Optional<Cart> findByUserId(final UserId userId) {
        final CartEntity cartEntity = cartDao.findByUserId(userId);
        if (cartEntity == null) {
            return Optional.empty();
        }
        return Optional.of(findCart(userId, cartEntity));
    }

    private Cart findCart(final UserId userId, final CartEntity cartEntity) {
        final List<CartProduct> updatedCartProductEntities = cartProductDao.findAllByCartId(
                        cartEntity.getCartId()).stream()
                .map(CartProductEntity::toDomain)
                .collect(Collectors.toList());
        return new Cart(cartEntity.getCartId(), userId, updatedCartProductEntities);
    }
}
