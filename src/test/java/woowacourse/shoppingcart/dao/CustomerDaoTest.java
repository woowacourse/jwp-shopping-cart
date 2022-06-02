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
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
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
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");

        // when
        Customer customer = customerDao.save(레넌);

        // then
        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("유저이름으로 회원 정보를 조회할 수 있다.")
    void findByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        Customer foundCustomer = customerDao.findByUsername("레넌");

        // then
        assertThat(foundCustomer.getEmail()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("유저이메일로 회원 정보를 조회할 수 있다.")
    void findByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        Customer foundCustomer = customerDao.findByEmail("rennon@woowa.com");

        // then
        assertThat(foundCustomer.getEmail()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("유저이름이 존재하는지 확인할 수 있다.")
    void existByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.existByUsername("레넌");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일이 존재하는지 확인할 수 있다.")
    void existByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.existByEmail("rennon@woowa.com");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원 이름에 따른 회원의 비밀번호가 일치한다.")
    void isValidPasswordByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.isValidPasswordByUsername("레넌", "1234");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회언 이름에 따른 회원의 비밀번호가 일치하지 않는다.")
    void isInvalidPasswordByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.isValidPasswordByUsername("레넌", "1235");

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("회원 이메일에 따른 회원의 비밀번호가 일치한다.")
    void isValidPasswordByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.isValidPasswordByEmail("rennon@woowa.com", "1234");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원 이메일에 따른 회원의 비밀번호가 일치하지 않는다.")
    void isInvalidPasswordByEmail() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        boolean result = customerDao.isValidPasswordByEmail("rennon@woowa.com", "1235");

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("비밀번호를 갱신할 수 있다.")
    void updatePassword() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        Customer customer = customerDao.save(레넌);

        // when
        customerDao.updatePassword(customer.getId(), "5678");
        boolean result = customerDao.isValidPasswordByUsername("레넌", "5678");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원을 지울 수 있다.")
    void deleteByUsername() {
        // given
        Customer 레넌 = new Customer("레넌", "rennon@woowa.com", "1234");
        customerDao.save(레넌);

        // when
        customerDao.deleteByUsername("레넌");

        // then
        assertThatThrownBy(() -> customerDao.findByUsername("레넌"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }
}
