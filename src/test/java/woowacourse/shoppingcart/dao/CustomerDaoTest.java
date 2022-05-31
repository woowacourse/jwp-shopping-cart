package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.customerDao = new CustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("이메일을 통해 회원 id 를 찾는다.")
    @Test
    void findIdByEmail() {
        // given
        final String email = "email@email.com";

        // when
        final Long customerId = customerDao.findIdByEmail(email);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 이메일을 통해 회원 id 를 찾을 경우 예외가 발생한다.")
    @Test
    void findIdByNotExistingEmail() {
        // given
        final String email = "notexistingemail@email.com";

        // when
        assertThatThrownBy(() -> customerDao.findIdByEmail(email))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("이메일과 비밀번호를 통해 회원엔티티를 찾는다.")
    @Test
    void findByEmailAndPassword() {
        // given
        final String email = "email@email.com";
        final String password = "password123!";

        // when
        final CustomerEntity customerEntity = customerDao.findByEmailAndPassword(email, password);
        final Customer customer = customerEntity.getCustomer();

        // then
        assertAll(
                () -> assertThat(customerEntity.getId()).isEqualTo(1L),
                () -> assertThat(customer).isEqualTo(new Customer(email, "파랑", "password123!"))
        );
    }

    @DisplayName("잘못된 이메일과 비밀번호를 통해 회원엔티티를 찾을 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, invalid123!", "notexistingemail@email.com, password123!",
            "notexistingemail@email.com, invalid123!"})
    void findByEmailAndPassword(final String email, final String password) {
        // when
        assertThatThrownBy(() -> customerDao.findByEmailAndPassword(email, password))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("아이디나 비밀번호를 잘못 입력했습니다.");
    }

    @DisplayName("중복된 이메일이 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, true", "notexistingemail@email.com, false"})
    void existEmail(final String email, final Boolean expected) {
        assertThat(customerDao.existEmail(email)).isEqualTo(expected);
    }

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void save() {
        String email = "newemail@email.com";
        String nickname = "쿼리치";
        String password = "password123!";

        assertThat(customerDao.save(email, nickname, password)).isNotNull();
    }

    @DisplayName("중복되는 이메일로 회원 정보를 저장할 시 예외가 발생한다.")
    @Test
    void saveFail() {
        String email = "email@email.com";
        String nickname = "쿼리치";
        String password = "password123!";

        assertThatThrownBy(() -> customerDao.save(email, nickname, password))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }
}
