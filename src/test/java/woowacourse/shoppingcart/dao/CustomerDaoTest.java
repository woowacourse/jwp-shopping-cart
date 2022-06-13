package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testData.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }


    @Test
    void 사용자의_이름으로_조회할_경우_객체를_리턴하는지_확인() {
        String userName = "puterism";
        String email = "crew01@naver.com";
        String pw = "a12345";

        final Customer actual = customerDao.findCustomerByUserName(userName);

        assertAll(() -> assertThat(actual.getUsername()).isEqualTo(userName),
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(new Password(actual.getPassword()).isSamePassword(new Password(pw))).isTrue());
    }

    @Test
    void 사용자의_이름이_존재하지않는_경우() {
        String userName = "gwangyeol-iM";

        assertThat(customerDao.isValidName(userName)).isFalse();
    }

    @Test
    void 사용자의_이메일이_존재하지_않는_경우() {
        String email = "me@google.com";

        assertThat(customerDao.isValidEmail(email)).isFalse();
    }

    @Test
    void 비밀번호_수정() {
        String name = "puterism";
        String newPassword = "a123456";

        customerDao.updatePassword(name, newPassword);
        Customer actual = customerDao.findCustomerByUserName(name);

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void 회원을_삭제하는_경우() {
        String name = "puterism";

        customerDao.deleteByName(name);

        assertThat(customerDao.isValidName(name)).isFalse();
    }

    @Test
    void 회원을_저장하는_경우() {
        String name = "alpha";
        String email = "bcc101106@gmail.com";
        String password = "123456";

        customerDao.saveCustomer(name, email, password);

        assertThat(customerDao.isValidName(name)).isTrue();
    }

    @Test
    void 존재하지_않는_이메일을_조회할_경우() {
        assertThatThrownBy(() -> customerDao.findCustomerByEmail("bcc0830@naver.com")).isInstanceOf(
                InvalidCustomerException.class);
    }
}
