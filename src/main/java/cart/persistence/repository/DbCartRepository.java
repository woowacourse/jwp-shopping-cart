package cart.persistence.repository;

import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.persistence.dao.CartProductDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.CartProductEntity;
import cart.persistence.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DbCartRepository implements CartRepository {

    private final CartProductDao cartProductDao;
    private final ProductDao productDao;

    public DbCartRepository(CartProductDao cartProductDao, ProductDao productDao) {
        this.cartProductDao = cartProductDao;
        this.productDao = productDao;
    }

    @Override
    public void create(long userId) {
    }

    @Override
    public Cart findByUserId(long userId) {
        List<CartProductEntity> cartProductEntities = this.cartProductDao.findAllByUserId();
        List<Product> products = new ArrayList<>();
        for (CartProductEntity cartProductEntity : cartProductEntities) {
            products.add(mapToProductFromCartProductEntity(cartProductEntity));
        }
        return Cart.createWithProducts(userId, products);
    }

    @Override
    public void update(Cart cart) {
        long memberId = cart.getMemberId();
        List<Product> products = cart.getProducts();
        products.stream()
                .map(product -> CartProductEntity.createToSave(memberId, product.getId()))
                .forEach(this::saveCartProductEntity);
        // TODO 삭제 로직이 없다! 추가하자
    }

    private Product mapToProductFromCartProductEntity(CartProductEntity cartProductEntity) {
        ProductEntity productEntity = this.productDao.findById(cartProductEntity.getProductId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 ID로 상품을 조회했습니다."));
        return Product.create(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl()
        );
    }

    private void saveCartProductEntity(CartProductEntity cartProductEntity) {
        this.cartProductDao.save(cartProductEntity);
    }
}
