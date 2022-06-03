package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.support.passwordencoder.PasswordEncoder;
import woowacourse.shoppingcart.support.passwordencoder.SimplePasswordEncoder;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private PasswordEncoder passwordEncoder;

    private final CustomerDao customerDao;
    private final String password;
    private final Customer customer;

    public CustomerDaoTest(final JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
        passwordEncoder = new SimplePasswordEncoder();
        password = passwordEncoder.encode("ehdgh1234");
        customer = Customer.of("dongho108", password, "01012123232", "인천 서구 검단로");
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

    @DisplayName("입력할 username이 이미 존재하면 True를 반환한다.")
    @Test
    void existByUsername() {
        // given
        final Customer customer = Customer.of("jjang9", "password1234", "01012123434", "서울시");
        customerDao.save(customer);

        // when
        boolean actual = customerDao.existsByUsername(customer.getUsername().getValue());

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void saveCustomer() {
        // given
        final Customer customer = Customer.of("dongho108", "ehdgh1234", "01012123232", "인천 서구 검단로");

        // when
        final Customer savedCustomer = customerDao.save(customer);

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
        final Customer savedCustomer = customerDao.save(customer);

        // when
        final Customer findCustomer = customerDao.findByUsername(customer.getUsername().getValue()).get();

        // then
        assertAll(
            () -> assertThat(findCustomer.getId()).isNotNull(),
            () -> assertThat(findCustomer.getUsername()).isEqualTo(savedCustomer.getUsername()),
            () -> assertThat(passwordEncoder.matches("ehdgh1234", findCustomer.getPassword().getValue())).isTrue(),
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(savedCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(savedCustomer.getAddress())
        );
    }

    @DisplayName("입력받은 Customer의 phoneNumber와 address를 수정한다.")
    @Test
    void update() {
        // given
        final Customer savedCustomer = customerDao.save(customer);
        final Customer newCustomer = Customer.of(savedCustomer.getId(), "dongho108", "ehdgh1234", "01000001111",
            "서울시 선릉역");

        // when
        customerDao.update(newCustomer);
        final Customer findCustomer = customerDao.findByUsername(newCustomer.getUsername().getValue()).get();

        // then
        assertAll(
            () -> assertThat(findCustomer.getPhoneNumber()).isEqualTo(newCustomer.getPhoneNumber()),
            () -> assertThat(findCustomer.getAddress()).isEqualTo(newCustomer.getAddress())
        );
    }

    @DisplayName("입력받은 Customer의 password를 변경한다.")
    @Test
    void updatePassword() {
        // given
        final Customer savedCustomer = customerDao.save(customer);
        final Customer newCustomer = Customer.of(savedCustomer.getId(), "dongho108", "ehdgh1111", "01012123232",
            "인천 서구 검단로");

        // when
        customerDao.update(newCustomer);
        final Customer findCustomer = customerDao.findByUsername(newCustomer.getUsername().getValue()).get();

        // then
        assertThat(findCustomer.getPassword()).isEqualTo(newCustomer.getPassword());
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteCustomer() {
        // given
        final Customer savedCustomer = customerDao.save(customer);

        // when
        customerDao.deleteByUsername(savedCustomer.getUsername().getValue());

        // then
        assertThat(customerDao.findByUsername(customer.getUsername().getValue()).isEmpty()).isTrue();
    }
}
