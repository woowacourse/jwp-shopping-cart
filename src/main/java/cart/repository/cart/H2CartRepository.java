package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.entiy.cart.CartEntity;
import cart.entiy.cart.CartEntityId;
import cart.entiy.cart.CartProductEntity;
import cart.entiy.user.UserEntityId;
import cart.exception.ProductNotFoundException;
import cart.repository.cart.dao.CartDao;
import cart.repository.cart.dao.CartProductDao;
import cart.repository.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        CartEntity cartEntity = CartEntity.from(cart);
        if (cartEntity.getCartId().getValue() == null) {
            cartEntity = cartDao.save(cartEntity);
        }

        saveCartProducts(cart, cartEntity);
        return findCart(cart.getUser(), cartEntity);
    }

    private void saveCartProducts(final Cart cart, final CartEntity cartEntity) {
        cartProductDao.deleteAllByCartId(cartEntity.getCartId());
        final List<CartProduct> cartProducts = cart.getCartProducts().getCartProducts();
        final List<CartProductEntity> cartProductEntities = toCartProductEntities(cartProducts,
                cartEntity.getCartId());
        cartProductDao.insertAll(cartProductEntities);
    }

    private List<CartProductEntity> toCartProductEntities(final List<CartProduct> cartProducts,
            final CartEntityId cartEntityId) {
        return cartProducts.stream()
                .map(cartProduct -> toCartProductEntity(cartEntityId, cartProduct))
                .collect(Collectors.toList());
    }

    private CartProductEntity toCartProductEntity(final CartEntityId cartEntityId, final CartProduct cartProduct) {
        return new CartProductEntity(
                cartProduct.getCartProductId(),
                cartEntityId,
                cartProduct.getProduct().getProductId()
        );
    }

    @Override
    public Optional<Cart> findByUser(final User user) {
        final CartEntity cartEntity = cartDao.findByUserId(UserEntityId.from(user.getUserId()));
        if (cartEntity == null) {
            return Optional.empty();
        }
        return Optional.of(findCart(user, cartEntity));
    }

    private Cart findCart(final User user, final CartEntity cartEntity) {
        final List<CartProductEntity> updatedCartProductEntities = cartProductDao.findAllByCartId(
                cartEntity.getCartId());
        final List<Product> products = productRepository.findAll();
        final List<CartProduct> cartProducts = updatedCartProductEntities.stream()
                .map(cartProductEntity -> findCartProduct(cartProductEntity, products))
                .collect(Collectors.toList());
        return new Cart(cartEntity.getCartId().toDomain(), user, cartProducts);
    }

    private CartProduct findCartProduct(final CartProductEntity cartProductEntity,
            final List<Product> products) {
        return products.stream()
                .filter(p -> p.getProductId().equals(cartProductEntity.getProductEntityId().toDomain()))
                .findAny()
                .map(product -> new CartProduct(cartProductEntity.getCartProductEntityId().toDomain(), product))
                .orElseThrow(() -> new ProductNotFoundException("해당 상품을 찾을 수 없습니다."));
    }
}
