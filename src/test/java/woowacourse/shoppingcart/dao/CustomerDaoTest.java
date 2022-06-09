package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // given
        Customer customer = Customer.of("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        Customer savedCustomer = customerDao.save(customer);

        // then
        assertAll(
            () -> assertThat(savedCustomer.getId()).isNotNull(),
            () -> assertThat(savedCustomer.getUsername()).isEqualTo(customer.getUsername()),
            () -> assertThat(savedCustomer.getPassword()).isEqualTo(customer.getPassword()),
            () -> assertThat(savedCustomer.getPhoneNumber()).isEqualTo(customer.getPhoneNumber()),
            () -> assertThat(savedCustomer.getAddress()).isEqualTo(customer.getAddress())
        );
    }

    @DisplayName("이름으로 회원을 조회한다.")
    @Test
    void findByUsername() {
        // given
        Customer customer = Customer.of("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        Customer savedCustomer = customerDao.save(customer);

        // when
        Customer findCustomer = customerDao.findByUsername(customer.getUsername().getValue()).get();

        // then
        assertAll(
            () -> assertThat(findCustomer.getId()).isNotNull(),
            () -> assertThat(findCustomer.getUsername()).isEqualTo(savedCustomer.getUsername()),
            () -> assertThat(findCustomer.getPassword()).isEqualTo(savedCustomer.getPassword()),
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(savedCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(savedCustomer.getAddress())
        );
    }

    @DisplayName("입력받은 Customer의 phoneNumber와 address를 수정한다.")
    @Test
    void update() {
        // given
        Customer customer = Customer.of("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        Customer savedCustomer = customerDao.save(customer);

        Customer newCustomer = Customer.of(savedCustomer.getId(), "dongho108", "ehdgh1234", "01000001111", "서울시 선릉역");
        customerDao.update(newCustomer);

        Customer findCustomer = customerDao.findByUsername(newCustomer.getUsername().getValue()).get();

        assertAll(
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(newCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(newCustomer.getAddress())
        );
    }

    @DisplayName("입력받은 Customer의 password를 변경한다.")
    @Test
    void updatePassword() {
        // given
        Customer customer = Customer.of("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        Customer savedCustomer = customerDao.save(customer);

        Customer newCustomer = Customer.of(savedCustomer.getId(), "dongho108", "ehdgh1111", "01000001111", "서울시 선릉역");
        customerDao.update(newCustomer);

        Customer findCustomer = customerDao.findByUsername(newCustomer.getUsername().getValue()).get();

        assertThat(findCustomer.getPassword()).isEqualTo(newCustomer.getPassword());
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteCustomer() {
        // given
        Customer customer = Customer.of("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");
        Customer savedCustomer = customerDao.save(customer);

        // when
        customerDao.deleteByUsername(savedCustomer.getUsername().getValue());

        // then
        assertThat(customerDao.findByUsername(customer.getUsername().getValue()).isEmpty()).isTrue();
    }
}
