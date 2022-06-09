package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import woowacourse.shoppingcart.exception.InvalidProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";

        // when
        Product product = productDao.save(new Product(name, price, imageUrl));
        Long productId = product.getId();

        // then
        assertThat(productId).isNotNull();
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        Long productId = productDao.save(new Product(name, price, imageUrl)).getId();
        Product expectedProduct = new Product(productId, name, price, imageUrl);

        // when
        Product product = productDao.findProductById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("페이지별 상품 목록 조회")
    void findProducts() {
        // given
        int size = 12;
        int offset = 0;

        // when
        List<Product> products = productDao.findProducts(size, offset);

        // then
        assertThat(products.get(0).getId()).isEqualTo(1L);
        assertThat(products).size().isEqualTo(12);
    }

    @Test
    @DisplayName("페이지별 상품 목록 조회에서 상품이 없으면 빈 리스트를 반환한다.")
    void findProductsWithEmpty() {
        // given
        int size = 1000;
        int offset = 1000;

        // when
        List<Product> products = productDao.findProducts(size, offset);

        // then
        assertThat(products).size().isEqualTo(0);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";

        Long productId = productDao.save(new Product(name, price, imageUrl)).getId();

        // when
        productDao.delete(productId);

        // then
        assertThatThrownBy(() -> productDao.findProductById(productId))
                .isInstanceOf(InvalidProductException.class);
    }
}
