package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.domain.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource) {
        customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "1234"));

        assertThat(customer.getId()).isNotNull();
    }

    @Test
    @DisplayName("이메일로 회원 정보를 찾는다.")
    void findByEmail() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "1234"));
        Customer foundCustomer = customerDao.findByEmail(customer.getEmail());

        assertThat(foundCustomer.getEmail()).isEqualTo("rennon@woowa.com");
    }

    @Test
    @DisplayName("존재하는 이메일과 비밀번호 확인한다.")
    void existEmail() {
        Customer customer = customerDao.save(new Customer("레넌", "rennon@woowa.com", "1234"));

        assertThat(customerDao.existByEmailAndPassword(customer.getEmail(), customer.getPassword())).isTrue();
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
}
