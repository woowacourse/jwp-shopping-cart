package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;
    private final String name = "초콜렛";
    private final int price = 1_000;
    private final String imageUrl = "www.test.com";

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, ID를 반환한다.")
    @Test
    void save() {
        Long productId = productDao.save(new Product(name, price, imageUrl));

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("ID를 상품을 찾으면, Product를 반환한다.")
    @Test
    void findProductById() {
        Long productId = productDao.save(new Product(name, price, imageUrl));
        Product expectedProduct = new Product(productId, name, price, imageUrl);

        Optional<Product> product = productDao.findProductById(productId);

        assertThat(product.get()).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("ID에 해당하는 상품이 없으면, 빈 Optional을 반환한다.")
    @Test
    void findProductByNotExistId() {
        Optional<Product> product = productDao.findProductById(100L);

        assertThat(product.isEmpty()).isTrue();
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        Long productId1 = productDao.save(new Product(name, price, imageUrl));
        Product expectedProduct1 = new Product(productId1, name, price, imageUrl);

        String name2 = "꼬북침";
        int price2 = 1_500;
        String imageUrl2 = "www.lotte.com";
        Long productId2 = productDao.save(new Product(name2, price2, imageUrl2));
        Product expectedProduct2 = new Product(productId2, name2, price2, imageUrl2);

        List<Product> products = productDao.findProducts();

        assertThat(products).containsExactly(expectedProduct1, expectedProduct2);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        Long productId = productDao.save(new Product(name, price, imageUrl));
        int beforeSize = productDao.findProducts().size();

        productDao.delete(productId);

        int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
