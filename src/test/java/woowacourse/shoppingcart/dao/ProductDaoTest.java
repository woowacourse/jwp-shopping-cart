package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.fixture.ProductFixtures;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // when
        final Long productId = productDao.save(ProductFixtures.PRODUCT_1);

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = productDao.save(ProductFixtures.PRODUCT_1);
        // when
        final Product product = productDao.findProductById(productId);

        // then
        assertThat(product.getName()).isEqualTo(ProductFixtures.PRODUCT_1.getName());
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final Long productId = productDao.save(ProductFixtures.PRODUCT_1);
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
