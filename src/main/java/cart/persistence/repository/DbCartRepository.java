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
    public Cart findByMemberId(long memberId) {
        List<CartProductEntity> cartProductEntities = this.cartProductDao.findAllByMemberId(memberId);
        List<Product> products = new ArrayList<>();
        for (CartProductEntity cartProductEntity : cartProductEntities) {
            products.add(mapToProductFromCartProductEntity(cartProductEntity));
        }
        return Cart.createWithProducts(memberId, products);
    }

    @Override
    public long saveProductToCart(Cart cartToAdd, Product product) {
        CartProductEntity cartProductEntity = CartProductEntity.createToSave(
                cartToAdd.getMemberId(),
                product.getId()
        );
        return this.cartProductDao.save(cartProductEntity);
    }

    @Override
    public void deleteProductFromCart(Cart cartToDelete, Product product) {

        CartProductEntity cartProductEntity = CartProductEntity.createToSave(
                cartToDelete.getMemberId(),
                product.getId()
        );
        this.cartProductDao.delete(cartProductEntity);
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
}
