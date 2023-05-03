package cart.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;


@JdbcTest
@Sql({"classpath:truncateTable.sql","classpath:productsTestData.sql","classpath:userTestData.sql"})
class JdbcUserTableDaoTest {

    private final JdbcUserTableDao jdbcUserTableDao;
    @Autowired
    public JdbcUserTableDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcUserTableDao = new JdbcUserTableDao(jdbcTemplate);
    }
    @Test
    @DisplayName("사용자 전부를 조회")
    void readAllTest(){
        Assertions.assertThat(jdbcUserTableDao.readAll()).hasSize(2);
    }

    @Test
    @DisplayName("사용자 한명을 조회")
    void readOneTest(){
        Assertions.assertThat(jdbcUserTableDao.readOne(1l).getEmail()).isEqualTo("test1@test1.com");
    }
}
