package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Customer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:customer.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("Customer DAO 테스트")
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("디비 ID 를 이용하여 회원 정보를 조회한다.")
    @Test
    void findById() {
        // given
        Customer savedCustomer = Customer.from(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        Customer customer = customerDao.findById(customerId).get();

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("test"),
                () -> assertThat(customer.getPassword()).isEqualTo("1234asdf!")
        );
    }

    @DisplayName("사용자 ID 를 이용하여 회원 정보를 조회한다.")
    @Test
    void findByUserId() {
        // when
        Customer customer = customerDao.findByUserId("puterism@woowacourse.com").get();

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("nickname"),
                () -> assertThat(customer.getPassword()).isEqualTo("1338ad00357397e37ec3990310efd04f767ab485fa8e69f2d06df186f9327372")
        );
    }

    @DisplayName("사용자 닉네임을 이용하여 회원 정보를 조회한다.")
    @Test
    void findByNickname() {
        // when
        Customer customer = customerDao.findByNickname("nickname").get();

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("nickname"),
                () -> assertThat(customer.getPassword()).isEqualTo("1338ad00357397e37ec3990310efd04f767ab485fa8e69f2d06df186f9327372")
        );
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void update() {
        // given
        Customer savedCustomer = Customer.from(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.updateNickname(customerId, "test2");

        // then
        Customer customer = customerDao.findById(customerId).get();
        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(customerId),
                () -> assertThat(customer.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("test2"),
                () -> assertThat(customer.getPassword()).isEqualTo("1234asdf!")
        );
    }

    @DisplayName("회원 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        // given
        Customer savedCustomer = Customer.from(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.updatePassword(customerId, "123dkhsd!");

        // then
        Customer customer = customerDao.findById(customerId).get();
        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(customerId),
                () -> assertThat(customer.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("test"),
                () -> assertThat(customer.getPassword()).isEqualTo("123dkhsd!")
        );
    }

    @DisplayName("회원정보를 지운다.")
    @Test
    void delete() {
        // given
        Customer savedCustomer = Customer.from(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.delete(customerId);

        // then
        Optional<Customer> customer = customerDao.findById(customerId);

        assertThat(customer.isEmpty()).isTrue();
    }
}
