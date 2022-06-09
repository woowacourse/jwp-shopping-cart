package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    void 사용자의_이름으로_조회할_경우_객체를_리턴하는지_확인() {

        // given
        final String userName = "puterism";

        // when
        final var actual = customerDao.findCustomerByUserName(userName);

        // then
        assertAll(
                () -> assertThat(actual.getUsername()).isEqualTo("puterism"),
                () -> assertThat(actual.getEmail()).isEqualTo("crew01@naver.com"),
                () -> assertThat(actual.getPassword()).isEqualTo("a12345")
        );
    }

    @Test
    void 사용자의_이름이_존재하지않는_경우() {
        // given
        final String userName = "gwangyeol-iM";

        // then
        assertThat(customerDao.isExistName(userName)).isFalse();
    }

    @Test
    void 사용자의_이메일이_존재하지_않는_경우() {
        final String email = "me@google.com";

        assertThat(customerDao.isExistEmail(email)).isFalse();
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

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void 회원을_삭제하는_경우() {
        final String name = "puterism";

        customerDao.deleteByName(name);

        assertThat(customerDao.isExistName(name)).isFalse();
    }

    @Test
    void 회원을_저장하는_경우() {
        final String name = "alpha";
        final String email = "bcc101106@gmail.com";
        final String password = "123";

        customerDao.saveCustomer(name, email, password);

        assertThat(customerDao.isExistName("alpha")).isTrue();
    }

    @Test
    void 존재하지_않는_이메일을_조회할_경우() {
        assertThatThrownBy(() -> customerDao.findCustomerByEmail("bcc0830@naver.com")).isInstanceOf(
                InvalidCustomerException.class);
    }
}
