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


@JdbcTest
class JdbcUserTableDaoTest {


    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserTableDao jdbcUserTableDao;
    @Autowired
    public JdbcUserTableDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserTableDao = new JdbcUserTableDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("insert into user_table(email,password) values('test1@test1.com','password1')");
        jdbcTemplate.execute("insert into user_table(email,password) values('test2@test2.com','password2')");
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
