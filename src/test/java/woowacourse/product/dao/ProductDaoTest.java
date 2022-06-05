package woowacourse.product.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.product.domain.Price;
import woowacourse.product.domain.Product;
import woowacourse.product.domain.Stock;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    @Autowired
    private DataSource dataSource;

    private ProductDao productDao;

    private final String name = "짱구";
    private final Price price = new Price(100_000_000);
    private final Stock stock = new Stock(1);
    private final String imageURL = "http://example.com/jjanggu.jpg";
    private final Product product = new Product(name, price, stock, imageURL);

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given

        // when
        final Long id = productDao.save(product);

        // then
        assertThat(id).isNotNull();
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = productDao.save(product);
        final Product expectedProduct = new Product(productId, name, price, stock, imageURL);

        // when
        final Product product = productDao.findProductById(productId).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }
    //
    // @DisplayName("상품 목록 조회")
    // @Test
    // void getProducts() {
    //
    //     // given
    //     final int size = 0;
    //
    //     // when
    //     final List<Product> products = productDao.findProducts();
    //
    //     // then
    //     assertThat(products).size().isEqualTo(size);
    // }
    //
    // @DisplayName("싱품 삭제")
    // @Test
    // void deleteProduct() {
    //     // given
    //     final String name = "초콜렛";
    //     final int price = 1_000;
    //     final String imageUrl = "www.test.com";
    //
    //     final Long productId = productDao.save(new Product(name, price, imageUrl));
    //     final int beforeSize = productDao.findProducts().size();
    //
    //     // when
    //     productDao.delete(productId);
    //
    //     // then
    //     final int afterSize = productDao.findProducts().size();
    //     assertThat(beforeSize - 1).isEqualTo(afterSize);
    // }
}
