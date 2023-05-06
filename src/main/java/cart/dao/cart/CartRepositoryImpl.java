package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.CartUserDao;
import cart.dao.user.CartUserEntity;
import cart.dao.user.userproduct.CartUserProductDao;
import cart.dao.user.userproduct.CartUserProductEntity;
import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.user.CartUser;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private final CartUserProductDao cartUserProductDao;
    private final CartUserDao cartUserDao;
    private final ProductDao productDao;

    public CartRepositoryImpl(final CartUserProductDao cartUserProductDao, final CartUserDao cartUserDao,
                              final ProductDao productDao) {
        this.cartUserProductDao = cartUserProductDao;
        this.cartUserDao = cartUserDao;
        this.productDao = productDao;
    }

    @Override
    public Long addProductInCart(final CartUser cartUser, final Product product) {
        final CartUserEntity cartEntity = cartUserDao.findByEmail(cartUser.getUserEmail());

        final CartUserProductEntity cartUserProductEntity =
                new CartUserProductEntity(cartEntity.getId(), product.getProductId());

        return cartUserProductDao.insert(cartUserProductEntity);
    }

    @Override
    public Cart findCartByCartUser(final CartUser cartUser) {
        final CartUserEntity cartUserEntity = cartUserDao.findByEmail(cartUser.getUserEmail());
        final List<ProductEntity> productEntities = cartUserProductDao.findProductByCartUserId(cartUserEntity.getId());

        final List<Product> products = productEntities.stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());

        return new Cart(cartUserEntity.toCartUser(), products);
    }

    @Override
    public List<Cart> findAll() {
        final List<CartUserEntity> allCartUsers = cartUserDao.findAll();
        final List<CartUserProductEntity> allCartUserProductEntities = cartUserProductDao.findAll();

        final List<Cart> results = new ArrayList<>();
        for (final CartUserEntity cartUser : allCartUsers) {
            final List<Long> productIds = filterUserProductsIds(allCartUserProductEntities, cartUser);

            final List<Product> products = productDao.findById(productIds)
                    .stream()
                    .map(ProductEntity::toProduct)
                    .collect(Collectors.toList());

            results.add(new Cart(cartUser.toCartUser(), products));
        }

        return results;
    }

    private List<Long> filterUserProductsIds(
            final List<CartUserProductEntity> allCartUserProductEntities,
            final CartUserEntity cartUser
    ) {
        return allCartUserProductEntities.stream()
                .filter(it -> it.getCartUserId().equals(cartUser.getId()))
                .map(CartUserProductEntity::getProductId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductInCart(final CartUser cartUser, final Product product) {
        final CartUserEntity cartUserEntity = cartUserDao.findByEmail(cartUser.getUserEmail());

        final int deleteCount = cartUserProductDao.deleteByCartUserIdAndProductId(
                cartUserEntity.getId(),
                product.getProductId()
        );

        validateModification(deleteCount);
    }

    private void validateModification(final int updateCount) {
        if (updateCount < 1) {
            throw new NoSuchElementException();
        }
    }
}
