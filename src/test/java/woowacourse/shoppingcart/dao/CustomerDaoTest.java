package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    void 사용자의_이름으로_조회할_경우_객체를_리턴하는지_확인() {

        // given
        final String userName = "puterism";

        // when
        final Customer actual = customerDao.findCustomerByUserName(userName);
        Customer expected = new Customer("puterism", "crew01@naver.com", "a123");

        // then
        assertThat(actual.equals(expected)).isTrue();
    }

    @Test
    void 사용자의_이름이_존재하지않는_경우() {
        // given
        final String userName = "gwangyeol-iM";

        // then
        assertThat(customerDao.isValidName(userName)).isFalse();
    }

    @Test
    void 사용자의_이메일이_존재하지_않는_경우() {
        final String email = "me@google.com";

        assertThat(customerDao.isValidEmail(email)).isFalse();
    }

    @Test
    void 비밀번호_수정() {
        //given
        final String name = "puterism";
        final String newPassword = "a12345";

        //when
        customerDao.updatePassword(name, newPassword);

        //then
        var actual = customerDao.findCustomerByUserName(name);

        assertThat(actual.isSamePassword(newPassword)).isTrue();
    }

    @Test
    void 회원을_삭제하는_경우() {
        final String name = "puterism";

        customerDao.deleteByName(name);

        assertThat(customerDao.isValidName(name)).isFalse();
    }

    @Test
    void 회원을_저장하는_경우() {
        final String name = "alpha";
        final String email = "bcc101106@gmail.com";
        final String password = "123";

        customerDao.saveCustomer(name, email, password);

        assertThat(customerDao.isValidName("alpha")).isTrue();
    }
}
