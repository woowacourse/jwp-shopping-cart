package woowacourse.shoppingcart.dao;

import static Fixture.ProductFixtures.*;
import static org.assertj.core.api.Assertions.*;

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

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        Long productId = productDao.save(CHICKEN);

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        Long productId = productDao.save(CHICKEN);
        Product expectedProduct = new Product(productId, CHICKEN_NAME, CHICKEN_PRICE, CHICKEN_IMAGE_URL,
                CHICKEN_SELLING, CHICKEN_DESCRIPTION);

        Product product = productDao.findProductById(productId).get();

        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        productDao.save(CHICKEN);

        List<Product> products = productDao.findSellingProducts();

        assertThat(products).size().isEqualTo(1);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        Long productId = productDao.save(CHICKEN);
        int beforeSize = productDao.findSellingProducts().size();

        productDao.delete(productId);

        int afterSize = productDao.findSellingProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
