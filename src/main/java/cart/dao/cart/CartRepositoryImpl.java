package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.UserDao;
import cart.domain.cart.Cart;
import cart.domain.product.*;
import cart.exception.GlobalException;
import cart.service.cart.CartRepository;
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
    public Long save(Cart cart, Long userId) {
        Product product = cart.getProduct();

        CartEntity cartEntity = new CartEntity(null, product.getId(), userId);

        return cartDao.insert(cartEntity);
    }

    @Override
    public List<Cart> findAllByUserId(Long userId) {
        List<CartEntity> cartEntities = cartDao.findByUserId(userId);
        userDao.findById(userId)
                .orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));

        return toCarts(cartEntities);
    }

    private List<Cart> toCarts(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(this::toCart)
                .collect(Collectors.toList());
    }

    private Cart toCart(CartEntity cartEntity) {
        ProductEntity productEntity = productDao.findById(cartEntity.getProductId())
                .orElseThrow(() -> new GlobalException("존재하지 않는 상품입니다."));

        return new Cart(toProduct(productEntity), cartEntity.getCartId());
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

    @Override
    public void deleteById(Long id) {
        cartDao.deleteById(id);
    }
}
