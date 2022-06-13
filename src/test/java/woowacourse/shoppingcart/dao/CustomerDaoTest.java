package woowacourse.shoppingcart.dao;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;

import woowacourse.shoppingcart.domain.customer.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("customer를 생성한다.")
    @Test
    void saveCustomer() {
        Customer savedCustomer = customerDao.save(MAT);

        assertAll(() -> {
            assertThat(savedCustomer.getId()).isNotNull();
            assertThat(savedCustomer.getUsername()).isEqualTo(MAT_USERNAME);
            assertThat(savedCustomer.getEmail()).isEqualTo(MAT_EMAIL);
            assertThat(savedCustomer.getPassword()).isEqualTo(MAT_ENCODED_PASSWORD);
            assertThat(savedCustomer.getAddress()).isEqualTo(MAT_ADDRESS);
            assertThat(savedCustomer.getPhoneNumber()).isEqualTo(MAT_PHONE_NUMBER);
        });
    }

    @DisplayName("username을 이용해 customer 를 조회한다.")
    @Test
    void findCustomerByUsername() {
        customerDao.save(YAHO);

        Optional<Customer> foundCustomer = customerDao.findByUsername(YAHO_USERNAME);

        assertThat(foundCustomer).isNotEmpty();
    }

    @DisplayName("customer 정보를 수정한다.")
    @Test
    void update() {
        Customer savedCustomer = customerDao.save(MAT);

        savedCustomer.modify(UPDATE_ADDRESS, UPDATE_PHONE_NUMBER);

        assertDoesNotThrow(() -> customerDao.update(savedCustomer));
    }

    @DisplayName("customer 정보를 삭제한다.")
    @Test
    void delete() {
        Customer savedCustomer = customerDao.save(YAHO);

        assertDoesNotThrow(() -> customerDao.delete(savedCustomer));
    }

    @DisplayName("username을 통해 조회를 하면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {
        Customer savedCustomer = customerDao.save(MAT);

        Long foundCustomerId = customerDao.findIdByUsername(MAT_USERNAME).getAsLong();

        assertThat(foundCustomerId).isEqualTo(savedCustomer.getId());
    }

    @DisplayName("email을 통해 조회를 하면, 있는 경우 email을 반환한다.")
    @Test
    void findEmailByEmail_exist() {
        customerDao.save(YAHO);

        Optional<String> email = customerDao.findEmailByEmail(YAHO_EMAIL);

        assertThat(email.get()).isEqualTo(YAHO_EMAIL);
    }

    @DisplayName("email을 통해 조회를 하면, 없는 경우 빈값을 반환한다.")
    @Test
    void findEmailByEmail_notExist() {
        customerDao.save(YAHO);

        Optional<String> email = customerDao.findEmailByEmail(MAT_EMAIL);

        assertThat(email).isEmpty();
    }
}
