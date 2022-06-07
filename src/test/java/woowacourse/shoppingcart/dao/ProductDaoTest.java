package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 상품_목록_조회() {
        var products = productDao.findProducts();

        assertThat(products.size()).isEqualTo(4);
    }
}
