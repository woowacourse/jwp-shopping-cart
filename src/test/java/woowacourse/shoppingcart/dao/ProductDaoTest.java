package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import woowacourse.shoppingcart.exception.InvalidProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
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
        final String imageUrl = "www.test.com";
        final int stock = 10;

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl, stock));

        // then
        assertThat(productId).isNotNull();
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        Product 캠핑_의자 = new Product(1L, "캠핑 의자", 35000,
                "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg", 10);

        // when
        final Product product = productDao.findProductById(1L);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(캠핑_의자);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 3;

        // when
        final List<Product> products = productDao.findProducts(size, 1);

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final int stock = 10;

        final Long productId = productDao.save(new Product(name, price, imageUrl, stock));

        // when
        productDao.delete(productId);

        // then
        assertThatThrownBy(() -> productDao.findProductById(productId))
                .isInstanceOf(InvalidProductException.class);
    }

    @DisplayName("전체 상품 개수 조회")
    @Test
    void findTotalCount() {
        assertThat(productDao.findTotalCount()).isEqualTo(19);
    }
}
