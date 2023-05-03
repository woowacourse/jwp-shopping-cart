package cart.dao;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

@JdbcTest
@Sql(scripts = {"classpath:truncateTable.sql","classpath:productsTestData.sql","classpath:userTestData.sql","classpath:cartTestData.sql"})
class JdbcCartDaoTest {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcCartDao jdbcCartDao;

    @Autowired
    public JdbcCartDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcCartDao = new JdbcCartDao(jdbcTemplate);
    }
    @Test
    void addProduct(){
        jdbcCartDao.addProduct(1l,1l);
        final Map<String, Object> actual = jdbcTemplate.queryForMap("SELECT * FROM cart_table ORDER BY id DESC LIMIT 1");
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.get("user_id")).isEqualTo(1l);
            softly.assertThat(actual.get("product_id")).isEqualTo(1l);
        });
    }

    @Test
    void readAll(){
        Assertions.assertThat(jdbcCartDao.readAll(1l)).hasSize(2);
    }

    @Test
    void delete(){
        jdbcCartDao.delete(1l);
        Assertions.assertThat(jdbcTemplate.query(
                "SELECT * FROM cart_table",
                        (rs,rowNum)->rs.getLong("id")
                )
        ).hasSize(1);
    }
}
