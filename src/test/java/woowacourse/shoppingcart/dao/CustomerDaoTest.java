package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CustomerDaoTest {

    @Autowired
    private DataSource dataSource;

    private CustomerDao customerDao;

    @BeforeEach
    public void setUp() {
        this.customerDao = new CustomerDao(dataSource);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void saveCustomer() {
        // given
        Customer customer = new Customer("email@email.com", "password123!A", "rookie");

        // when
        Long id = customerDao.save(customer);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("이메일과 패스워드를 통해서 회원을 조회할 수 있다.")
    void findByEmailAndPassword() {
        // given
        customerDao.save(new Customer("email@email.com", "password123!A", "rookie"));

        // when
        Customer customer = customerDao.findByEmailAndPassword("email@email.com", "password123!A").get();

        // then
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(new Customer(1L, "email@email.com", "password123!A", "rookie"));
    }

//    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
//    @Test
//    void findIdByUserNameTest() {
//
//        // given
//        final String userName = "puterism";
//
//        // when
//        final Long customerId = customerDao.findIdByUserName(userName);
//
//        // then
//        assertThat(customerId).isEqualTo(1L);
//    }
//
//    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
//    @Test
//    void findIdByUserNameTestIgnoreUpperLowerCase() {
//        // given
//        final String userName = "gwangyeol-iM";
//
//        // when
//        final Long customerId = customerDao.findIdByUserName(userName);
//
//        // then
//        assertThat(customerId).isEqualTo(16L);
//    }
}
