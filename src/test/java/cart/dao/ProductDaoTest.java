package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;


@Import(ProductDao.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("상품 전체 조회 테스트")
    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO product (name, price) VALUES ('Chicken', 18000), ('Pizza', 24000)");

        List<ProductEntity> all = productDao.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting("name")
                .contains("Chicken", "Pizza");
        assertThat(all).extracting("price")
                .contains(18000, 24000);

    }
}
