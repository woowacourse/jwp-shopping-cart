package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    private final ProductRepository productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new Product(name, price, imageUrl));
        final Product expectedProduct = new Product(productId, name, price, imageUrl);

        // when
        final Product product = productDao.findProductById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회(page * size부터 size 개수 만큼 반환)")
    @Test
    void getProducts() {
        // given
        for (int i = 1; i <= 10; i++) {
            productDao.save(new Product("name" + i, i * 1000, "imageUrl" + i));
        }
        final int size = 3; // 3개
        final int page = 2; // 2페이지

        // when
        final List<Product> products = productDao.findProducts(size, page); // 4, 5, 6 번째 데이터

        // then
        assertAll(
                () -> assertThat(products).size().isEqualTo(size),
                () -> assertThat(products.get(0).getPrice()).isEqualTo(4000),
                () -> assertThat(products.get(1).getPrice()).isEqualTo(5000),
                () -> assertThat(products.get(2).getPrice()).isEqualTo(6000)
        );
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Long productId = productDao.save(new Product(name, price, imageUrl));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
