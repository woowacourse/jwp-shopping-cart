package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.entity.Customer;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("아이디가 존재하는지 확인한다.")
    @Test
    void existCustomerByUserId() {
        // when
        boolean actual = customerDao.existCustomerByUserId("puterism@woowacourse.com");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("닉네임이 존재하는지 확인한다.")
    @Test
    void existCustomerByNickname() {
        // when
        boolean actual = customerDao.existCustomerByNickname("nickname1");

        // then
        assertThat(actual).isEqualTo(true);
    }

    @DisplayName("로그인 정보를 이용하여 사용자 정보를 조회한다,")
    @Test
    void findByUserId() {
        // when
        Customer customer = customerDao.findByUserId("puterism@woowacourse.com");

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("nickname"),
                () -> assertThat(customer.getPassword()).isEqualTo("1234asdf!")
        );
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findById() {
        // given
        Customer savedCustomer = new Customer(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        Customer customer = customerDao.findById(customerId);

        // then
        assertAll(
                () -> assertThat(customer.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customer.getNickname()).isEqualTo("test"),
                () -> assertThat(customer.getPassword()).isEqualTo("1234asdf!")
        );
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void update() {
        // given
        Customer savedCustomer = new Customer(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.update(customerId, "test2");

        // then
        Customer customer = customerDao.findById(customerId);
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
        Customer savedCustomer = new Customer(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.updatePassword(customerId, "123dkhsd!");

        // then
        Customer customer = customerDao.findById(customerId);
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
        Customer savedCustomer = new Customer(null, "test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerDao.save(savedCustomer);

        // when
        customerDao.delete(customerId);

        // then
        assertThatThrownBy(() -> customerDao.findById(customerId))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
