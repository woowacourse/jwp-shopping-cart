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
import woowacourse.shoppingcart.domain.Customer;

import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("email, nickname, password를 통해 customer를 생성한다.")
    @Test
    void saveCustomer() {
        // given
        Customer customer = new Customer("awesomeo@gmail.com", "awesomeo", "Password123!");

        // when
        Long savedId = customerDao.save(customer);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String nickname = "test4";
        customerDao.save(new Customer("test4@naver.com", "test4", "Password123!"));

        // when
        final Long customerId = customerDao.findIdByNickname(nickname);

        // then
        assertThat(customerId).isNotNull();
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String nickname = "Test4";
        customerDao.save(new Customer("test4@naver.com", "test4", "Password123!"));

        // when
        final Long customerId = customerDao.findIdByNickname(nickname);

        // then
        assertThat(customerId).isNotNull();
    }

    @DisplayName("닉네임이 중복될 경우, 참을 반환한다.")
    @Test
    void existsByNickname() {

        // given
        String nickname = "beom1234";
        customerDao.save(new Customer("beomWhale@naver.com", nickname, "Password123!"));

        // when && then
        assertThat(customerDao.existsByNickname(nickname)).isTrue();
    }

    @DisplayName("email을 통해 Customer를 찾는다.")
    @Test
    void findIdByEmail() {

        // given
        String email = "beomWhale@naver.com";
        Long savedId = customerDao.save(new Customer(email, "beom1234", "Password123!"));

        // when
        Customer customer = customerDao.findIdByEmail(email).get();

        // then
        assertThat(savedId).isEqualTo(customer.getId());
    }

    @DisplayName("email을 통해 Customer를 삭제한다.")
    @Test
    void deleteByEmail() {
        // given
        String email = "beomWhale@naver.com";
        customerDao.save(new Customer(email, "beom1234", "Password123!"));

        // when
        customerDao.deleteByEmail(email);

        // then
        Optional<Customer> maybeCustomer = customerDao.findIdByEmail(email);
        assertThat(maybeCustomer.isEmpty()).isTrue();
    }
}
