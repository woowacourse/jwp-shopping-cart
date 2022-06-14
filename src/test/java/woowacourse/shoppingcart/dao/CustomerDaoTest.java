package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장할 수 있다.")
    void save() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");

        // when
        Customer customer = customerDao.save(레넌);

        // then
        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("유저이름으로 회원 정보를 조회할 수 있다.")
    void findByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        customerDao.save(레넌);

        // when
        Customer foundCustomer = customerDao.findByUsername(new Username("레넌"));

        // then
        assertThat(foundCustomer.getEmail().getValue()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("유저이메일로 회원 정보를 조회할 수 있다.")
    void findByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        customerDao.save(레넌);

        // when
        Customer foundCustomer = customerDao.findByEmail(new Email("rennon@woowa.com"));

        // then
        assertThat(foundCustomer.getEmail().getValue()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("유저이름이 존재하는지 확인할 수 있다.")
    void existByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.existByUsername(new Username("레넌"));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일이 존재하는지 확인할 수 있다.")
    void existByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.existByEmail(new Email("rennon@woowa.com"));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("비밀번호를 갱신할 수 있다.")
    void updatePassword() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        Customer customer = customerDao.save(레넌);

        // when
        customerDao.updatePassword(customer.getId(), new Password("567890"));
        Customer foundCustomer = customerDao.findByUsername(new Username("레넌"));

        // then
        assertThat(foundCustomer.getPassword().getValue()).isEqualTo("567890");
    }

    @Test
    @DisplayName("회원을 지울 수 있다.")
    void deleteByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "123456");
        customerDao.save(레넌);

        // when
        customerDao.deleteByUsername(new Username("레넌"));

        // then
        assertThatThrownBy(() -> customerDao.findByUsername(new Username("레넌")))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }
}
