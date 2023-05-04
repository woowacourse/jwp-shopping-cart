package cart.dao.user.userproduct;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.CartUserDao;
import cart.dao.user.CartUserEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@Import({CartUserProductDao.class, CartUserDao.class, ProductDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartUserProductDaoTest {
    @Autowired
    private CartUserProductDao cartUserProductDao;

    @Autowired
    private CartUserDao cartUserDao;

    @Autowired
    private ProductDao productDao;

    @DisplayName("장바구니 내 상품 추가 테스트")
    @Test
    void insertCartUserProduct() {
        final Long userId = cartUserDao.insert(new CartUserEntity("a@a.com", "password"));
        final Long productId = productDao.insert(new ProductEntity("ProductA", 13_000, "ETC", "image.com"));
        final CartUserProductEntity cartUserProductEntity = new CartUserProductEntity(
                userId,
                productId
        );

        cartUserProductDao.insert(cartUserProductEntity);

        final List<CartUserProductEntity> entities = cartUserProductDao.findAll();
        assertThat(entities).hasSize(1);
    }

    @DisplayName("장바구니 내 상품 조회 테스트")
    @Test
    void findProductByCartUserId() {
        final Long userId = cartUserDao.insert(new CartUserEntity("a@a.com", "password"));
        final Long productId = productDao.insert(new ProductEntity("ProductA", 13_000, "ETC", "image.com"));
        final CartUserProductEntity cartUserProductEntity = new CartUserProductEntity(userId, productId);
        cartUserProductDao.insert(cartUserProductEntity);
        cartUserProductDao.insert(cartUserProductEntity);

        final List<ProductEntity> productEntitiesInCart = cartUserProductDao.findProductByCartUserId(userId);

        assertThat(productEntitiesInCart).hasSize(2);
    }

    @DisplayName("장바구니 내 상품 제거 테스트")
    @Test
    void deleteByCartUserIdAndProductId() {
        //given
        final Long userId = cartUserDao.insert(new CartUserEntity("a@a.com", "password"));
        final Long productId = productDao.insert(new ProductEntity("ProductA", 13_000, "ETC", "image.com"));

        final CartUserProductEntity cartUserProductEntity = new CartUserProductEntity(userId, productId);
        cartUserProductDao.insert(cartUserProductEntity);
        final List<CartUserProductEntity> entities = cartUserProductDao.findAll();
        assertThat(entities).hasSize(1);

        //when
        cartUserProductDao.deleteByCartUserIdAndProductId(userId, productId);

        //then
        final List<CartUserProductEntity> afterDeleteEntities = cartUserProductDao.findAll();
        assertThat(afterDeleteEntities).hasSize(0);
    }
}
