package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("customer를 생성한다.")
    @Test
    void saveCustomer() {
        Customer customer = new Customer(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);

        Customer savedCustomer = customerDao.save(customer);

        assertAll(() -> {
            assertThat(savedCustomer.getId()).isNotNull();
            assertThat(savedCustomer.getUsername()).isEqualTo(USERNAME);
            assertThat(savedCustomer.getEmail()).isEqualTo(EMAIL);
            assertThat(savedCustomer.getPassword()).isEqualTo(PASSWORD);
            assertThat(savedCustomer.getAddress()).isEqualTo(ADDRESS);
            assertThat(savedCustomer.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        });
    }

    @DisplayName("username을 이용해 customer 를 조회한다.")
    @Test
    void findCustomerByUsername() {
        Customer customer = new Customer(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        customerDao.save(customer);

        Optional<Customer> foundCustomer = customerDao.findByUsername(USERNAME);

        assertThat(foundCustomer.isPresent()).isTrue();
    }

    @DisplayName("customer 정보를 수정한다.")
    @Test
    void update() {
        Customer customer = new Customer(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        Customer savedCustomer = customerDao.save(customer);

        assertDoesNotThrow(() -> customerDao.update(savedCustomer));
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUsername(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUsername(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }
}
