package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

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
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final int stock = 1;
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new Product(name, price, stock, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final int stock = 1;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        final Product expectedProduct = new Product(productId, name, price, stock, imageUrl);

        // when
        final Product product = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
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

    @DisplayName("상품 존재 여부 확인")
    @Test
    void exists() {
        final String name = "초콜렛";
        final int price = 1_000;
        final int stock = 1;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new Product(name, price, stock, imageUrl));

        boolean exists = productDao.exists(productId);

        assertThat(exists).isTrue();
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final int stock = 1;

        final Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @DisplayName("상품 재고 업데이트")
    @Test
    void updateStock() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        int stock = 1;

        Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        Product product = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));;
        product.receive(1);
        productDao.updateStock(product);
        Product updated = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));;

        assertThat(updated.getStock()).isEqualTo(2);
    }
}
