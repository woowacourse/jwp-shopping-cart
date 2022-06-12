package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerRepository customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("유저이름으로 회원 정보를 찾는다.")
    void findByUsername() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));
        Customer foundCustomer = customerDao.findByUsername(customer.getUsername());

        assertThat(foundCustomer.getEmail()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("존재하는 이메일과 비밀번호 확인한다.")
    void existEmail() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customerDao.isValidPasswordByEmail(customer.getEmail(), customer.getPassword())).isTrue();
    }

    @Test
    @DisplayName("유저이름이 존재하는지 확인한다.")
    void existByUserName() {
        customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customerDao.existByUsername("레넌")).isTrue();
    }

    @Test
    @DisplayName("유저 이름이 존재하지 않으면?")
    void existByUserNameFalse() {
        customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customerDao.existByUsername("그린론")).isFalse();
    }

    @Test
    @DisplayName("비밀번호가 일치한다.")
    void isValidPassword() {
        customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customerDao.isValidPasswordByUsername("레넌", "12345678")).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않는다.")
    void isInvalidPassword() {
        customerDao.save(new Customer("레넌", "rennon@woowa.com", "12345678"));

        assertThat(customerDao.isValidPasswordByUsername("레넌", "34561278")).isFalse();
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Customer customer = customerDao.findByUsername(userName);

        // then
        assertThat(customer.getUsername()).isEqualTo(userName);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Customer customer = customerDao.findByUsername(userName);

        // then
        assertThat(customer.getId()).isEqualTo(16L);
    }
}
