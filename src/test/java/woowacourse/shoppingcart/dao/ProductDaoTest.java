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
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.domain.ThumbnailImage;
import woowacourse.shoppingcart.product.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private static final String NAME = "초콜렛";
    private static final Integer PRICE = 1_000;
    private static final Long STOCK_QUANTITY = 10L;
    private static final ThumbnailImage IMAGE = new ThumbnailImage("www.test.com", "alt");

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면 id값을 가진 Product 객체를 반환한다.")
    @Test
    void save() {
        // when
        final Product product = productDao.save(new Product(NAME, PRICE, STOCK_QUANTITY, IMAGE));

        // then
        assertThat(product.getId()).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Product expectedProduct = productDao.save(new Product(NAME, PRICE, STOCK_QUANTITY, IMAGE));

        // when
        final Product product = productDao.findProductById(expectedProduct.getId());

        // then
        assertThat(product.getId()).isEqualTo(expectedProduct.getId());
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 1;
        productDao.save(new Product(NAME, PRICE, STOCK_QUANTITY, IMAGE));

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products.size()).isEqualTo(size);
    }
}
