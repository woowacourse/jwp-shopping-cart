package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
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
        assertThat(productId).isEqualTo(1L);
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

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 0;

        // when
        final Products products = productDao.findProducts();

        // then
        assertThat(products.getValue()).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";

        Long productId = productDao.save(new Product(name, price, imageUrl)).getId();
        int beforeSize = productDao.findProducts().getValue().size();

        // when
        productDao.delete(productId);

        // then
        int afterSize = productDao.findProducts().getValue().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
