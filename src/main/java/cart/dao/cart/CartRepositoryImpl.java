package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.UserDao;
import cart.dao.user.UserEntity;
import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.*;
import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;
import cart.exception.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private final CartDao cartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public CartRepositoryImpl(CartDao cartDao, UserDao userDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public Long save(Cart cart) {
        Product product = cart.getProduct();
        User user = cart.getUser();

        CartEntity cartEntity = new CartEntity(null, product.getId(), user.getId());

        return cartDao.insert(cartEntity);
    }

    @Override
    public List<Cart> findAllByUserId(Long userId) {
        List<CartEntity> cartEntities = cartDao.findByUserId(userId);
        UserEntity userEntity = userDao.findById(userId)
                .orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));

        return toCarts(toUser(userEntity), cartEntities);
    }

    private List<Cart> toCarts(User user, List<CartEntity> cartEntities) {
        List<Long> productIds = getProductIds(cartEntities);

        List<ProductEntity> productEntities = productDao.findByIds(productIds);

        return productEntities.stream()
                .map(productEntity -> new Cart(user, toProduct(productEntity)))
                .collect(Collectors.toList());
    }

    private List<Long> getProductIds(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(CartEntity::getProductId)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductEntity productEntity) {
        return new Product(
                ProductName.from(productEntity.getName()),
                ProductPrice.from(productEntity.getPrice()),
                ProductCategory.valueOf(productEntity.getCategory()),
                ImageUrl.from(productEntity.getImageUrl()),
                productEntity.getId()
        );
    }

    private User toUser(UserEntity userEntity) {
        return new User(
                Email.from(userEntity.getEmail()),
                Password.from(userEntity.getPassword()),
                userEntity.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        cartDao.deleteById(id);
    }
}
