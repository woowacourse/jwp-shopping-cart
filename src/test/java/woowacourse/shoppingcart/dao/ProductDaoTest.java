package woowacourse.shoppingcart.dao;

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
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "http://www.test.com";

        Long productId = productDao.save(new Product(name, price, imageUrl, true, "상세 설명"));

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "http://www.test.com";
        Long productId = productDao.save(new Product(name, price, imageUrl, true, "상세 설명"));
        Product expectedProduct = new Product(productId, name, price, imageUrl, true, "상세 설명");

        Product product = productDao.findProductById(productId).get();

        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "http://www.test.com";
        productDao.save(new Product(name, price, imageUrl, true, "상세 설명"));

        List<Product> products = productDao.findSellingProducts();

        assertThat(products).size().isEqualTo(1);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "http://www.test.com";

        Long productId = productDao.save(new Product(name, price, imageUrl, true, "상세 설명"));
        int beforeSize = productDao.findSellingProducts().size();

        productDao.delete(productId);

        int afterSize = productDao.findSellingProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
