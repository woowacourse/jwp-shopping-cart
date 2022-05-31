package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final String NAME = "썬";
    private static final String EMAIL = "sunyong@gmail.com";
    private static final String PASSWORD = "12345678";

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        customerDao = new CustomerDao(namedParameterJdbcTemplate);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUserName(userName)
                .orElseThrow();

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUserName(userName)
                .orElseThrow();

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("회원 정보를 저장한다.")
    void save() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);

        // when
        final Customer actual = customerDao.save(customer);

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("중복된 이메일이 있는지 확인한다.")
    void existsByEmail() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        customerDao.save(customer);

        // when
        final Customer duplicatedEmailCustomer = new Customer("라라", EMAIL, PASSWORD);

        // then
        assertThat(customerDao.existsByEmail(duplicatedEmailCustomer)).isTrue();
    }

    @Test
    @DisplayName("이메일에 해당하는 회원 객체를 검색한다.")
    void findByEmail() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        customerDao.save(customer);

        // when
        Customer actual = customerDao.findByEmail(customer.getEmail()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("id로 회원 객체를 검색한다.")
    void findById() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Customer savedCustomer = customerDao.save(customer);

        // when
        Customer actual = customerDao.findById(savedCustomer.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(savedCustomer);
    }
}
