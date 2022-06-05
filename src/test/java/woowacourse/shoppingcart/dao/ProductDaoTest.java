package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
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
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
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
        final Product product = productDao.findProductById(productId);

        // then
        assertThat(product).isEqualTo(PRODUCT_1);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        productDao.save(PRODUCT_1);
        productDao.save(PRODUCT_2);

        final int size = 2;

        // when
        final List<Product> products = productDao.findProducts();

        // then

        assertThat(products).hasSize(2);
    }
//
//    @DisplayName("싱품 삭제")
//    @Test
//    void deleteProduct() {
//        // given
//        final String name = "초콜렛";
//        final int price = 1_000;
//        final String imageUrl = "www.test.com";
//
//        final Long productId = productDao.save(new Product(name, price, imageUrl));
//        final int beforeSize = productDao.findProducts().size();
//
//        // when
//        productDao.delete(productId);
//
//        // then
//        final int afterSize = productDao.findProducts().size();
//        assertThat(beforeSize - 1).isEqualTo(afterSize);
//    }
}
