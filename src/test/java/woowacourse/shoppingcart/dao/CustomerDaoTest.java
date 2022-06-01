package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    @DisplayName("이메일, 암호화 된 패스워드, 닉네임을 받아서 Customer 테이블에 저장한다.")
    void save() {
        final Customer testCustomer = Customer.createWithoutId("test@test.com", "testtest", "테스트");
        final Long createdCustomerId = customerDao.save(testCustomer);

        final Customer findCustomer = customerDao.findById(createdCustomerId).get();

        assertThat(findCustomer.getId()).isEqualTo(createdCustomerId);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String username = "puterism";

        // when
        final Long customerId = customerDao.findByUsername(username);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String username = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findByUsername(username);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("email을 통해 customer를 찾아서 반환한다.")
    void findByEmail() {
        final String email = "test1@test.com";

        final Customer customer = customerDao.findByEmail(email).get();

        assertThat(customer.getEmail()).isEqualTo(email);

    }

    @Test
    @DisplayName("Customer 정보를 입력받아 기존의 Customer 정보를 수정한다.")
    void update() {
        final Customer testCustomer = Customer.createWithoutId("test@test.com", "testtest", "테스트");
        final Long createdCustomerId = customerDao.save(testCustomer);

        final Customer findCustomer = customerDao.findById(createdCustomerId).get();

        final String changedName = "바뀐이름";
        final Customer changeForm = Customer.createWithoutPassword(
                findCustomer.getId(),
                findCustomer.getEmail(),
                changedName
        );

        customerDao.update(changeForm);

        final Customer changedCustomer = customerDao.findById(createdCustomerId).get();

        assertThat(changedCustomer.getUsername()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("Customer 정보를 입력받아 기존의 Customer 정보를 수정할 때, 중복된 이름이 있으면 예외가 발생한다.")
    void update_duplicateNameException() {
        final String duplicateName = "테스트2";
        final Customer testCustomer1 = Customer.createWithoutId("test1@test.com", "testtest", "테스트1");
        final Customer testCustomer2 = Customer.createWithoutId("test2@test.com", "testtest", duplicateName);
        final Long createdCustomerId = customerDao.save(testCustomer1);
        customerDao.save(testCustomer2);

        final Customer findCustomer = customerDao.findById(createdCustomerId).get();

        final Customer changeForm = Customer.createWithoutPassword(
                findCustomer.getId(),
                findCustomer.getEmail(),
                duplicateName
        );

        assertThatThrownBy(() -> customerDao.update(changeForm))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Customer의 id를 받아서 일치하는 Customer를 삭제한다.")
    void deleteById() {
        final Customer testCustomer = Customer.createWithoutId("test@test.com", "testtest", "테스트");
        final Long createdCustomerId = customerDao.save(testCustomer);
        final Customer findCustomer = customerDao.findById(createdCustomerId).get();

        customerDao.deleteById(findCustomer.getId());
        final Optional<Customer> empty = customerDao.findById(findCustomer.getId());

        assertThat(empty).isEmpty();
    }
}