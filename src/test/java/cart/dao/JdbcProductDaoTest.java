package cart.dao;

import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:data.sql"})
class JdbcProductDaoTest {

    private JdbcProductDao jdbcProductDao;

    private JdbcProductDaoTest(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcProductDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Product 삽입 테스트")
    void insertTest() {
        Long id = jdbcProductDao.insert(new Product("IO", 10000, null));
        assertThat(id).isPositive();
    }
}
