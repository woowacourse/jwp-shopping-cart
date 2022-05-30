package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;

import javax.sql.DataSource;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwe123!@#";

    private final CustomerDao customerDao;

    CustomerDaoTest(final DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        final Customer customer = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        final Long customerId = customerDao.save(customer);

        assertThat(customerId).isGreaterThan(0);
    }

    @DisplayName("회원을 아이디로 조회한다.")
    @Test
    void findById() {
        final Customer expected = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        final Long customerId = customerDao.save(expected);
        final Optional<Customer> actual = customerDao.findById(customerId);

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("회원을 이메일로 조회한다.")
    @Test
    void findByEmail() {
        final Customer expected = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        customerDao.save(expected);
        final Optional<Customer> actual = customerDao.findByEmail(CUSTOMER_EMAIL);

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("이메일에 해당하는 회원의 존재 유무를 확인한다.")
    @Test
    void existsByEmail() {
        final Customer expected = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        customerDao.save(expected);

        assertThat(customerDao.existsByEmail(CUSTOMER_EMAIL)).isTrue();
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteById() {
        final Customer customer = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        final Long customerId = customerDao.save(customer);

        customerDao.deleteById(customerId);

        assertThat(customerDao.findByEmail(customer.getEmail())).isEmpty();
    }
}
