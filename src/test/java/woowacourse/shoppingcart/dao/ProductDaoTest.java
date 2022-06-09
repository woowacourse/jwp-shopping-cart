package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import woowacourse.shoppingcart.ShoppingCartTest;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductDaoTest extends ShoppingCartTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품 목록을 페이징 조회한다.")
    @Test
    void findProducts() {
        List<Product> products = productDao.findProducts(3, 10);
        List<Long> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toUnmodifiableList());

        assertAll(
                () -> assertThat(productIds).hasSize(10),
                () -> assertThat(productIds).containsExactly(4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L)
        );
    }

    @DisplayName("id에 해당하는 상품을 반환한다.")
    @Test
    void findById() {
        Product product = productDao.findById(1L);

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(1L),
                () -> assertThat(product.getName()).isEqualTo("캠핑 의자")
        );
    }

    @DisplayName("상품의 총 개수를 반환한다.")
    @Test
    void getTotalCount() {
        int count = productDao.getTotalCount();

        assertThat(count).isEqualTo(19);
    }

    @DisplayName("상품 id가 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"3, true", "20, false"})
    void checkIdExistence(long id, boolean expected) {
        boolean actual = productDao.checkIdExistence(id);

        assertThat(actual).isEqualTo(expected);
    }
}
