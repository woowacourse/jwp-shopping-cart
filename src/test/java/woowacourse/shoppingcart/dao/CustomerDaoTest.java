package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.support.HashPasswordEncoder;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodePassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.RawPassword;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:test_schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(JdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @DisplayName("회원가입 시 입력한 정보를 저장한다.")
    @Test
    void saveCustomer() {
        //given
        final Customer customer =
                Customer.of("kth990303", encode("kth@@990303"), "keikeikei", 23);

        //when
        final Long id = customerDao.save(customer);

        //then
        assertThat(id).isNotNull();
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {

        // given
        final String username = "puterism";

        // when
        final Long customerId = customerDao.findIdByUsername(username);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {

        // given
        final String username = "gwangyeol-iM";

        // when
        final Long customerId = customerDao.findIdByUsername(username);

        // then
        assertThat(customerId).isEqualTo(16L);
    }

    @DisplayName("비밀번호를 업데이트한다.")
    @Test
    void updatePassword() {
        final String username = "kth990303";
        final Customer given = Customer.of(username, encode("kth@990202"), "김태현", 21);
        final Customer expected = Customer.of(username, encode("kth@990303"), "김태현", 21);

        customerDao.save(given);
        customerDao.updatePassword(expected.getPassword(), username);

        Customer actual = customerDao.findCustomerByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @DisplayName("비밀번호를 제외한 회원 정보를 업데이트한다.")
    @Test
    void updateInfo() {
        final String username = "kth990303";
        final Customer given = Customer.of(username, encode("kth@990303"), "김태현", 21);
        final Customer expected = Customer.of(username, encode("kth@990303"), "케이", 23);

        customerDao.save(given);
        customerDao.updateInfo(expected.getUsername(), expected.getNickname(), expected.getAge());

        Customer actual = customerDao.findCustomerByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
        assertAll(
                () -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname()),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge())
        );
    }

    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        Customer given = Customer.of("forky", encode("forky@1234"), "복희", 26);

        customerDao.save(given);
        customerDao.delete(given);

        assertThatExceptionOfType(InvalidCustomerException.class)
                .isThrownBy(() -> customerDao.findIdByUsername(given.getUsername()))
                .withMessageContaining("존재");
    }

    private EncodePassword encode(String rawPassword) {
        RawPassword password = new RawPassword(rawPassword);
        PasswordEncoder passwordEncoder = new HashPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
