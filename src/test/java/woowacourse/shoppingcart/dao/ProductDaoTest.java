package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.support.test.ExtendedJdbcTest;

@ExtendedJdbcTest
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "http://www.test.com";
        final String description = "this is sample-description";

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl, description));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "http://www.test.com";
        final String description = "this is sample-description";

        final Long productId = productDao.save(new Product(name, price, imageUrl, description));
        final Product expectedProduct = new Product(productId, name, price, imageUrl, description);

        // when
        final Product product = productDao.findProductById(productId).orElseThrow();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("잘못된 상품ID로 찾으면, empty를 반환한다.")
    @Test
    void returnEmptyOnInvalidProductId() {
        // given
        Long invalidProductId = 999L;

        // when
        final Optional<Product> product = productDao.findProductById(invalidProductId);

        // then
        assertThat(product).isEmpty();
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
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "http://www.test.com";
        final String description = "this is sample-description";

        final Long productId = productDao.save(new Product(name, price, imageUrl, description));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
