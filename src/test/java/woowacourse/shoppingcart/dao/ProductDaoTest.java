package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductId;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품 목록 조회가 정상적으로 되는지 확인한다.")
    @Test
    void getProducts() {
        final List<Product> products = productDao.getProducts();
        final int result = products.size();

        assertThat(result).isEqualTo(3);
    }

    @DisplayName("상품이 존재하는 것을 확인한다.")
    @Test
    void exists_true() {
        final Boolean result = productDao.exists(new ProductId(1));

        assertThat(result).isTrue();
    }

    @DisplayName("상품이 존재하지 않는 것을 확인한다.")
    @Test
    void exists_false() {
        final Boolean result = productDao.exists(new ProductId(4));

        assertThat(result).isFalse();
    }
}
