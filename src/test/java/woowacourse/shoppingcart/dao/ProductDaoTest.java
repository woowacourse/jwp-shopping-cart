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
        Product firstExpected = new Product(1L, "캠핑 의자", 35000,
                100, "https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg");
        Product secondExpected = new Product(2L, "그릴", 100000,
                100, "https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg");
        Product thirdExpected = new Product(3L, "아이스박스", 20000,
                100, "https://thawing-fortress-83192.herokuapp.com/static/images/icebox.jpg");

        List<Product> products = productDao.findAll();

        assertThat(products).isEqualTo(List.of(firstExpected, secondExpected, thirdExpected));
    }

    @DisplayName("상품의 재고를 반환한다.")
    @Test
    void findStockById() {
        int stock = productDao.findStockById(1L);

        assertThat(stock).isEqualTo(100);
    }
}
