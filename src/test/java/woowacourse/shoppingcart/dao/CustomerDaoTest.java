package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "12345678a";
    public static final String NICKNAME = "nick";


    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("Customer를 저장한다.")
    @Test
    void saveCustomerTest() {
        final Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);

        Customer savedCustomer = customerDao.save(customer);

        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("email를 통해 찾은 Customer가 존재하면 Optional<Customer>를 반환한다.")
    @Test
    void findExistingCustomerByEmail() {
        final Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);
        customerDao.save(customer);

        Optional<Customer> foundCustomer = customerDao.findByEmail(EMAIL);

        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("email를 통해 찾은 Customer가 존재하지 않으면 Optional Empty를 반환한다.")
    @Test
    void findNotExistingCustomerByEmail() {
        Optional<Customer> foundCustomer = customerDao.findByEmail(EMAIL);

        assertThat(foundCustomer).isEmpty();
    }

    @DisplayName("id를 통해 찾은 Customer가 존재하면 Optional<Customer>를 반환한다.")
    @Test
    void findExistingCustomerById() {
        final Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);
        Customer savedCustomer = customerDao.save(customer);

        Optional<Customer> foundCustomer = customerDao.findById(savedCustomer.getId());

        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @DisplayName("email를 통해 찾은 Customer가 존재하지 않으면 Optional Empty를 반환한다.")
    @Test
    void findNotExistingCustomerById() {
        Optional<Customer> foundCustomer = customerDao.findById(0L);

        assertThat(foundCustomer).isEmpty();
    }

    @DisplayName("email로 회원을 존재 여부를 반환한다.")
    @ParameterizedTest
    @MethodSource("emailAndResult")
    void existByEmail(String email, boolean expected) {
        final Customer customer = new Customer(EMAIL, PASSWORD, NICKNAME);
        customerDao.save(customer);
        boolean actual = customerDao.existByEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> emailAndResult() {
        return Stream.of(
                Arguments.of(EMAIL, true),
                Arguments.of("notFoundEmail@email.com", false)
        );
    }
}
