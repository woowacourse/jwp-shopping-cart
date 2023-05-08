package cart.cart.persistence;

import cart.cart.domain.Cart;
import cart.cart.domain.CartRepository;
import cart.exception.NoSuchDataExistException;
import cart.product.domain.Product;
import cart.product.persistence.ProductDao;
import cart.product.persistence.ProductEntity;
import cart.user.domain.CartUser;
import cart.user.persistence.CartUserDao;
import cart.user.persistence.CartUserEntity;
import java.util.ArrayList;
import java.util.List;
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
        if (isNotExistCartUser(cartUser.getUserEmail())) {
            throw new NoSuchDataExistException("존재 하지 않는 사용자입니다.");
        }
        final CartUserEntity cartEntity = cartUserDao.findByEmail(cartUser.getUserEmail());

        final CartUserProductEntity cartUserProductEntity =
                new CartUserProductEntity(cartEntity.getId(), product.getProductId());

        return cartUserProductDao.insert(cartUserProductEntity);
    }

    @Override
    public Cart findCartByCartUser(final CartUser cartUser) {
        if (isNotExistCartUser(cartUser.getUserEmail())) {
            throw new NoSuchDataExistException("존재 하지 않는 사용자의 장바구니입니다.");
        }
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
            throw new NoSuchDataExistException();
        }
    }

    private boolean isNotExistCartUser(final String email) {
        final int count = cartUserDao.countByEmail(email);

        if (count < 1) {
            return true;
        }
        return false;
    }
}
