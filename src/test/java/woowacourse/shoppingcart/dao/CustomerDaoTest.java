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
        final Customer customer = new Customer("썬", "sunyong@gmail.com", "12345678");

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
        final Customer customer = new Customer("썬", "sunyong@gmail.com", "12345678");
        customerDao.save(customer);

        // when
        final Customer duplicatedEmailCustomer = new Customer("라라", "sunyong@gmail.com", "12345678");

        // then
        assertThat(customerDao.existsByEmail(duplicatedEmailCustomer)).isTrue();
    }
}
