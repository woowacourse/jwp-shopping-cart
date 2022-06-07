package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:schema.sql")
public class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        final Long productId = productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        final Long productId = productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        final Product product = productDao.findProductById(productId);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("초콜렛"),
                () -> assertThat(product.getPrice()).isEqualTo(1_000),
                () -> assertThat(product.getImageUrl()).isEqualTo("www.test.com")
        );
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        final List<Product> products = productDao.findProducts();

        assertThat(products).size().isEqualTo(1);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        final Long productId = productDao.save(createProduct("초콜렛", 1_000, "www.test.com"));

        productDao.delete(productId);

        assertThatThrownBy(() -> productDao.findProductById(productId))
                .isInstanceOf(InvalidProductException.class);
    }
}
