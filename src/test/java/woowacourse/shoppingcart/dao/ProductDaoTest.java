package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.ProductFixture.PAPER;
import static woowacourse.shoppingcart.ProductFixture.CHEESE;
import static woowacourse.shoppingcart.ProductFixture.PEN;
import static woowacourse.shoppingcart.ProductFixture.WATER;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(1L, WATER),
                Arguments.of(2L, CHEESE),
                Arguments.of(3L, PAPER),
                Arguments.of(4L, PEN)
        );
    }

    @Test
    void 상품_목록_조회() {
        var products = productDao.findProducts();

        assertThat(products.size()).isEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource("provide")
    void 상품_ID로_조회() {
        var products = productDao.findProductById(1L);

        assertThat(products).isEqualTo(WATER);
    }
}
