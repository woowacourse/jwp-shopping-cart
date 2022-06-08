package woowacourse.shoppingcart.unit.product.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(final JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = 1L;
        final Product expectedProduct = new Product(productId, "사과", 1600, "apple.co.kr");

        // when
        final Product product = productDao.findProductById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("productID를 상품을 찾지 못하면, 예외를 던진다.")
    @Test
    void findProductById_notExistProduct_ExceptionThrown() {
        // when, then
        assertThatThrownBy(() -> productDao.findProductById(999L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 5;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }
}
