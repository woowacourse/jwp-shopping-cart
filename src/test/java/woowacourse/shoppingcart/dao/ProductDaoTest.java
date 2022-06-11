package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.global.DaoTest;
import woowacourse.shoppingcart.domain.Product;

public class ProductDaoTest extends DaoTest {

    private static final int 초기_데이터_수 = 30;

    private static final String name = "초콜렛";
    private static final int price = 1_000;
    private static final String imageUrl = "www.test.com";
    private static final Long quantity = 2_000L;
    private static Product TEST_PRODUCT;
    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource,
                          NamedParameterJdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(dataSource, jdbcTemplate);
        this.TEST_PRODUCT = createTestProduct();
    }

    private Product createTestProduct() {

        return new Product(name, price, imageUrl, quantity);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given & when
        final Long productId = productDao.save(TEST_PRODUCT);

        // then
        assertThat(productId).isEqualTo(초기_데이터_수 + 1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = productDao.save(TEST_PRODUCT);
        final Product expectedProduct = new Product(productId, name, price, imageUrl, quantity);

        // when
        final Product product = productDao.findProductById(productId).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 초기_데이터_수 + 0;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final Long productId = productDao.save(TEST_PRODUCT);
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.deleteById(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
