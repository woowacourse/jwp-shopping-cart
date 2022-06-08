package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new JdbcProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // when
        final Long productId = productDao.save(PRODUCT_1);

        // then
        assertThat(productId).isPositive();
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = productDao.save(PRODUCT_1);

        // when
        final ProductEntity product = productDao.findProductById(productId);

        // then
        assertThat(product).extracting("id", "name", "price", "imageUrl", "description", "stock")
                .containsExactly(productId, PRODUCT_1.getName(), PRODUCT_1.getPrice().getValue(),
                        PRODUCT_1.getImageUrl(), PRODUCT_1.getDescription(), PRODUCT_1.getStock().getValue());
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);

        final int size = 2;

        // when
        final List<ProductEntity> products = productDao.findProducts();

        // then

        assertThat(products).hasSize(2).extracting("id", "name", "price", "imageUrl", "description", "stock")
                .contains(tuple(productId1, PRODUCT_1.getName(), PRODUCT_1.getPrice().getValue(),
                        PRODUCT_1.getImageUrl(), PRODUCT_1.getDescription(), PRODUCT_1.getStock().getValue())
                        .tuple(productId2, PRODUCT_2.getName(), PRODUCT_2.getPrice().getValue(),
                                PRODUCT_2.getImageUrl(), PRODUCT_2.getDescription(), PRODUCT_2.getStock().getValue()));
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final Long productId = productDao.save(PRODUCT_1);
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
