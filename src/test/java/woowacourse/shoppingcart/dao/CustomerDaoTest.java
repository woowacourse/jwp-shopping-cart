package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.customerDao = new CustomerDao(jdbcTemplate, dataSource);
    }

    @Test
    @DisplayName("customer를 등록한다.")
    void saveCustomer() {
        Customer customer =
                new Customer("email", "Pw123456!", "name", "010-1234-5678", "address");
        Long customerId = customerDao.save(customer);
        assertThat(customerId).isEqualTo(1L);
    }

    @Test
    @DisplayName("email과 password가 일치하는 customer의 id를 반환한다.")
    void findIdByEmailAndPassword() {
        Customer customer =
                new Customer("email", "Pw123456!", "name", "010-1234-5678", "address");
        Long customerId = customerDao.save(customer);

        Long responseId = customerDao.findIdByEmailAndPassword("email", "Pw123456!").get();

        assertThat(responseId).isEqualTo(customerId);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByName(userName);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @DisplayName("email이 존재하는지 확인한다.")
    @Test
    void existEmail() {
        //given
        String email = "email";
        Customer customer =
                new Customer(email, "Pw123456!", "name", "010-1234-5678", "address");
        customerDao.save(customer);

        //when
        boolean isExistEmail = customerDao.existEmail(email);

        //then
        assertThat(isExistEmail).isTrue();
    }
}
