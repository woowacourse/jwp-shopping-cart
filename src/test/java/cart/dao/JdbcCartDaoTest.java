package cart.dao;

import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:data.sql"})
public class JdbcCartDaoTest {

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image")
            );
    private final JdbcCartDao jdbcCartDao;
    private final JdbcTemplate jdbcTemplate;

    private JdbcCartDaoTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcCartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("Cart 삽입 테스트")
    void insertTest() {
        final Long id = jdbcCartDao.insert(1L, 1L);
        assertThat(id).isPositive();
    }

    @Test
    @DisplayName("Cart UserId로 조회 테스트")
    void findByIdTest() {
        jdbcCartDao.insert(1L, 1L);
        jdbcCartDao.insert(1L, 2L);

        assertThat(jdbcCartDao.findByUserId(1L)).extracting("productId").contains(1L, 2L);
    }
}
