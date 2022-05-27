package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("customer를 이름으로 검색할 수 있다.")
    void findByUsername() {
        // given
        Customer customer = Customer.Builder()
                .username("username")
                .password("password123")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        customerDao.save(customer);

        // when
        Customer findCustomer = customerDao.findByUsername("username");

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("customner를 저장하면 아이디를 반환한다.")
    void save() {
        // given
        Customer customer = Customer.Builder()
                .username("username")
                .password("password123")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();

        // when
        Long customerId = customerDao.save(customer);

        // then
        assertThat(customerId).isNotNull();
    }

    @Test
    @DisplayName("username이 이미 존재하는지 확인한다.")
    void existUsername() {
        // given
        Customer customer = Customer.Builder()
                .username("username")
                .password("password123")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();

        // when
        customerDao.save(customer);

        // then
        assertThat(customerDao.existCustomerByUsername("username")).isTrue();
    }

    @Test
    @DisplayName("유저를 유저 이름으로 찾아 삭제한다.")
    void deleteByUsername() {
        // given
        Customer customer = Customer.Builder()
                .username("username")
                .password("password123")
                .phoneNumber("01012345678")
                .address("성담빌딩")
                .build();
        customerDao.save(customer);

        // when & then
        assertThat(customerDao.deleteByUsername("username")).isEqualTo(1);
    }
}
