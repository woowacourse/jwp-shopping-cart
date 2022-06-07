package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findAll() {
        List<Product> products = productDao.findAll();

        assertThat(products).hasSize(19);
    }

    @DisplayName("상품의 재고를 반환한다.")
    @Test
    void findStockById() {
        int stock = productDao.findStockById(1L);

        assertThat(stock).isEqualTo(100);
    }

    @DisplayName("상품 id가 존재하는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"3, true", "20, false"})
    void existsId(long id, boolean expected) {
        boolean actual = productDao.existsId(id);

        assertThat(actual).isEqualTo(expected);
    }
}
