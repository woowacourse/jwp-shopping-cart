package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final String NAME = "썬";
    private static final String RAW_EMAIL = "sunyong@gmail.com";
    private static final Email EMAIL = new Email(RAW_EMAIL);
    private static final String RAW_PASSWORD = "12345678";
    private static final PlainPassword PLAIN_PASSWORD = new PlainPassword(RAW_PASSWORD);
    private static final EncodedPassword PASSWORD = new EncodedPassword(PLAIN_PASSWORD.encode());

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        customerDao = new CustomerDao(namedParameterJdbcTemplate);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {
        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUserName(userName)
                .orElseThrow();

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {
        // given
        final String userName = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUserName(userName)
                .orElseThrow();

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @Test
    @DisplayName("회원 정보를 저장한다.")
    void save() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);

        // when
        final Customer actual = customerDao.save(customer);

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("중복된 이메일이 있는지 확인한다.")
    void existsByEmail() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        customerDao.save(customer);

        // when
        final Customer duplicatedEmailCustomer = new Customer("라라", EMAIL, PASSWORD);

        // then
        assertThat(customerDao.existsByEmail(duplicatedEmailCustomer)).isTrue();
    }

    @Test
    @DisplayName("이메일에 해당하는 회원을 검색한다.")
    void findByEmail() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        customerDao.save(customer);

        // when
        Customer actual = customerDao.findByEmail(customer.getEmail()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("id로 회원을 검색한다.")
    void findById() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Customer savedCustomer = customerDao.save(customer);

        // when
        Customer actual = customerDao.findById(savedCustomer.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(savedCustomer);
    }

    @Test
    @DisplayName("id로 회원을 삭제한다.")
    void deleteById() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Customer savedCustomer = customerDao.save(customer);

        // when
        final int actual = customerDao.deleteById(savedCustomer.getId());

        // then
        assertThat(actual).isOne();
    }

    @Test
    @DisplayName("id에 해당하는 회원의 이름을 수정한다.")
    void updateById_newName() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Customer savedCustomer = customerDao.save(customer);
        final String newName = "라라";

        // when
        final Customer updatedCustomer = savedCustomer.updateName(newName);
        customerDao.updateById(updatedCustomer);
        final Customer actual = customerDao.findById(updatedCustomer.getId()).get();

        // then
        assertThat(actual.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("id에 해당하는 회원의 비밀번호를 수정한다.")
    void updateById_newPassword() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Customer savedCustomer = customerDao.save(customer);
        final PlainPassword plainPassword = new PlainPassword("newpassword123");
        final EncodedPassword newPassword = new EncodedPassword(plainPassword.encode());

        // when
        final Customer updatedCustomer = new Customer(savedCustomer.getId(), NAME, EMAIL, newPassword);
        customerDao.updateById(updatedCustomer);
        final Customer actual = customerDao.findById(updatedCustomer.getId()).get();

        // then
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
