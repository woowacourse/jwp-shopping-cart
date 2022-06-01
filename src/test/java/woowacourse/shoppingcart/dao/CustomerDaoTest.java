package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        boolean actual = customerDao.existCustomerByUserId("puterism@woowacourse.com");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("닉네임이 존재하는지 확인한다.")
    @Test
    void existCustomerByNickname() {
        // when
        boolean actual = customerDao.existCustomerByNickname("nickname1");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("로그인 정보가 존재하는지 확인한다.")
    @Test
    void existCustomer() {
        // when
        boolean actual = customerDao.existCustomer("puterism@woowacourse.com", "1234asdf!");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("로그인 정보를 이용하여 사용자 정보를 조회한다,")
    @Test
    void find() {
        // when
        Customer customer = customerDao.findByUserId("puterism@woowacourse.com");

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("nickname"),
                () -> assertThat(customer.getPassword()).isEqualTo("1234asdf!")
        );
    }
}
