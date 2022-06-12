package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Nickname;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final Customer 파랑 = new Customer("email@email.com", "파랑", "password123!");
    private static final Email EMAIL = new Email("email@email.com");
    private static final Password PASSWORD = new Password("password123!");
    private static final Email NOT_EXISTING_EMAIL = new Email("notexistingemail@email.com");

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.customerDao = new CustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("이메일을 통해 회원 id 를 찾는다.")
    @Test
    void findIdByEmail() {
        // given & when
        final Long customerId = customerDao.findIdByEmail(EMAIL);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 이메일을 통해 회원 id 를 찾을 경우 예외가 발생한다.")
    @Test
    void findIdByNotExistingEmail() {
        assertThatThrownBy(() -> customerDao.findIdByEmail(NOT_EXISTING_EMAIL))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("이메일을 통해 회원엔티티를 찾는다.")
    @Test
    void findByEmail() {
        // given & when
        final CustomerEntity customerEntity = customerDao.findByEmail(EMAIL);
        final Customer customer = customerEntity.getCustomer();

        // then
        assertThat(customer).isEqualTo(파랑);
    }

    @DisplayName("존재하지 않는 이메일을 통해 회원엔티티를 찾을 경우 예외가 발생한다.")
    @Test
    void findByEmailFail() {
        assertThatThrownBy(() -> customerDao.findByEmail(NOT_EXISTING_EMAIL))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("이메일과 비밀번호를 통해 회원엔티티를 찾는다.")
    @Test
    void findByEmailAndPassword() {
        final CustomerEntity customerEntity = customerDao.findByEmailAndPassword(EMAIL, PASSWORD)
                .orElseThrow();
        final Customer customer = customerEntity.getCustomer();

        assertAll(
                () -> assertThat(customerEntity.getId()).isEqualTo(1L),
                () -> assertThat(customer).isEqualTo(파랑)
        );
    }

    @DisplayName("잘못된 이메일과 비밀번호를 통해 회원엔티티를 찾을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, invalid123!", "notexistingemail@email.com, password123!",
            "notexistingemail@email.com, invalid123!"})
    void findByEmailAndPassword(final String email, final String password) {
        assertThat(customerDao.findByEmailAndPassword(new Email(email), new Password(password))).isEmpty();
    }

    @DisplayName("중복된 이메일이 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, true", "notexistingemail@email.com, false"})
    void existEmail(final String email, final Boolean expected) {
        assertThat(customerDao.existEmail(new Email(email))).isEqualTo(expected);
    }

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void save() {
        final Customer customer = new Customer("newemail@email.com", "쿼리치", "password123!");

        assertThat(customerDao.save(customer)).isNotNull();
    }

    @DisplayName("중복되는 이메일로 회원 정보를 저장할 시 예외가 발생한다.")
    @Test
    void saveFail() {
        final Customer customer = new Customer("email@email.com", "쿼리치", "password123!");

        assertThatThrownBy(() -> customerDao.save(customer))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateProfile() {
        final Nickname 파리채 = new Nickname("파리채");

        customerDao.updateProfile(EMAIL, 파리채);

        CustomerEntity customerEntity = customerDao.findByEmail(EMAIL);
        assertThat(customerEntity.getCustomer().getNickname()).isEqualTo(파리채.getValue());
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        final Password password = new Password("newpassword123!");

        customerDao.updatePassword(EMAIL, password);

        final CustomerEntity customerEntity = customerDao.findByEmail(EMAIL);
        assertThat(customerEntity.getCustomer().getPassword()).isEqualTo(password.getValue());
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void delete() {
        customerDao.delete(EMAIL);

        assertThatThrownBy(() -> customerDao.findByEmail(EMAIL))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }
}
