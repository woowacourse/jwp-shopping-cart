package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.TestFixture.product1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.TestFixture;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.nobodyexception.NotFoundProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(value = {"/schema.sql", "/data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }


    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById_exist_productReturned() {
        // given
        // when
        final Product product = productDao.findProductById(1L);

        // then
        assertThat(product).isEqualTo(product1);
    }

    @DisplayName("존재하지 않은 productId를 상품을 찾으면, 예외를 발생시킨다.")
    @Test
    void findProductById_notExist_exceptionThrown() {
        // when, then

        assertThatThrownBy(() -> productDao.findProductById(30L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findProducts() {
        // given

        // when
        List<Product> products = productDao.findProducts();

        // then
        assertThat(products.size()).isEqualTo(20);
    }
}
