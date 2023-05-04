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
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final CartUserProductDao cartUserProductDao;
    private final CartUserDao cartUserDao;
    private final ProductDao productDao;

    public CartRepositoryImpl(CartUserProductDao cartUserProductDao, CartUserDao cartUserDao, ProductDao productDao) {
        this.cartUserProductDao = cartUserProductDao;
        this.cartUserDao = cartUserDao;
        this.productDao = productDao;
    }

    @Override
    public Long addProductInCart(Cart cart, Product product) {
        CartUser cartUser = cart.getCartUser();
        CartUserEntity cartEntity = cartUserDao.findByEmail(cartUser.getUserEmail());

        CartUserProductEntity cartUserProductEntity =
                new CartUserProductEntity(cartEntity.getId(), product.getProductId());

        return cartUserProductDao.insert(cartUserProductEntity);
    }

    @Override
    public Cart findCartByCartUser(CartUser cartUser) {
        CartUserEntity cartUserEntity = cartUserDao.findByEmail(cartUser.getUserEmail());
        List<Long> productIds = cartUserProductDao.findProductByCartUserId(cartUserEntity.getId())
                .stream()
                .map(CartUserProductEntity::getProductId)
                .collect(Collectors.toList());

        List<ProductEntity> productEntities = productDao.findById(productIds);
        List<Product> products = productEntities.stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());

        return new Cart(cartUserEntity.toCartUser(), products);
    }

    @Override
    public List<Cart> findAll() {
        List<CartUserEntity> allCartUsers = cartUserDao.findAll();
        List<CartUserProductEntity> allCartUserProductEntities = cartUserProductDao.findAll();

        List<Cart> results = new ArrayList<>();
        for (CartUserEntity cartUser : allCartUsers) {
            List<Long> productIds = filterUserProductsIds(allCartUserProductEntities, cartUser);

            List<Product> products = productDao.findById(productIds)
                    .stream()
                    .map(ProductEntity::toProduct)
                    .collect(Collectors.toList());

            results.add(new Cart(cartUser.toCartUser(), products));
        }

        return results;
    }

    private List<Long> filterUserProductsIds(
            List<CartUserProductEntity> allCartUserProductEntities,
            CartUserEntity cartUser
    ) {
        return allCartUserProductEntities.stream()
                .filter(it -> it.getCartUserId().equals(cartUser.getId()))
                .map(CartUserProductEntity::getProductId)
                .collect(Collectors.toList());
    }
}
