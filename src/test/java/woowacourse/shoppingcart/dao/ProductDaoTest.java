package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findById() {
        // given
        Product 캠핑_의자 = new Product(1L, "캠핑 의자", 35000,
                "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg", 10);

        // when
        final Product product = productDao.findById(1L);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(캠핑_의자);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findByPage() {
        // given
        final int limit = 3;

        // when
        final List<Product> products = productDao.findByPage(limit, 1);

        // then
        assertThat(products).hasSize(limit)
                .extracting(Product::getId)
                .containsExactly(2L, 3L, 4L);
    }

    @DisplayName("상품 목록 조회 - 전체 상품 개수를 넘어가는 offset")
    @Test
    void findByPageFail() {
        // given
        final int limit = 3;

        // when
        final List<Product> products = productDao.findByPage(limit, 100);

        // then
        assertThat(products).hasSize(0);
    }

    @DisplayName("전체 상품 개수 조회")
    @Test
    void findTotalCount() {
        assertThat(productDao.findTotalCount()).isEqualTo(19);
    }
}
