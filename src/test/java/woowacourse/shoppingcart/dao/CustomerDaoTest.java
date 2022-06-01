package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("아이디가 존재하는지 확인한다.")
    @Test
    void existCustomerByUserId() {
        // when
        boolean actual = customerDao.existCustomerByUserId("puterism");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("아이디가 존재하는지 확인한다.")
    @Test
    void existCustomerByNickname() {
        // when
        boolean actual = customerDao.existCustomerByNickname("nickname1");

        // then
        assertThat(actual).isEqualTo(true);
    }
}
