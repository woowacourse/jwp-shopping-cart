package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Account;
import woowacourse.shoppingcart.domain.customer.Address;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Nickname;
import woowacourse.shoppingcart.domain.customer.PhoneNumber;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        // given
        Account account = new Account("hamcheeseburger");
        Nickname nickname = new Nickname("corinne");
        Address address = new Address("address");
        PhoneNumber phoneNumber = new PhoneNumber("01012345678");
        final Customer customer = new Customer(account, nickname, "Password123!", address, phoneNumber);
        // when
        final Customer savedCustomer = customerDao.save(customer);
        // then
        assertAll(
                () -> assertThat(savedCustomer.getId()).isEqualTo(2L),
                () -> assertThat(savedCustomer.getAccount()).isEqualTo("hamcheeseburger"),
                () -> assertThat(savedCustomer.getNickname()).isEqualTo("corinne"),
                () -> assertThat(savedCustomer.getPassword()).isEqualTo("Password123!"),
                () -> assertThat(savedCustomer.getAddress()).isEqualTo("address"),
                () -> assertThat(savedCustomer.getPhoneNumber()).isEqualTo("01012345678")
        );
    }

    @Test
    @DisplayName("회원을 id로 조회한다.")
    void findById() {
        // given
        final Optional<Customer> customer = customerDao.findById(1L);

        // when
        assert (customer.isPresent());

        // then
        final Customer foundCustomer = customer.get();
        assertAll(
                () -> assertThat(foundCustomer.getAccount()).isEqualTo("pobi"),
                () -> assertThat(foundCustomer.getNickname()).isEqualTo("eden"),
                () -> assertThat(foundCustomer.getPassword()).isEqualTo("Password123!"),
                () -> assertThat(foundCustomer.getAddress()).isEqualTo("address"),
                () -> assertThat(foundCustomer.getPhoneNumber()).isEqualTo("01012345678")
        );
    }

    @Test
    @DisplayName("회원을 account 로 조회한다.")
    void findByAccount() {
        // given
        final Optional<Customer> customer = customerDao.findByAccount("pobi");

        // when
        assert (customer.isPresent());

        // then
        final Customer foundCustomer = customer.get();
        assertAll(
                () -> assertThat(foundCustomer.getAccount()).isEqualTo("pobi"),
                () -> assertThat(foundCustomer.getNickname()).isEqualTo("eden"),
                () -> assertThat(foundCustomer.getPassword()).isEqualTo("Password123!"),
                () -> assertThat(foundCustomer.getAddress()).isEqualTo("address"),
                () -> assertThat(foundCustomer.getPhoneNumber()).isEqualTo("01012345678")
        );
    }

    @Test
    @DisplayName("회원의 정보를 수정한다.")
    void updateById() {
        // given
        String nickname = "eden22";
        String address = "new address";
        String phoneNumber = "01012341234";

        // when
        final int affectedRows = customerDao.update(1L, nickname, address, phoneNumber);
        final Optional<Customer> customer = customerDao.findById(1L);

        assert (customer.isPresent());

        final Customer actual = customer.get();
        // then
        assertAll(
                () -> assertThat(affectedRows).isEqualTo(1),
                () -> assertThat(actual.getNickname()).isEqualTo("eden22"),
                () -> assertThat(actual.getAddress()).isEqualTo("new address"),
                () -> assertThat(actual.getPhoneNumber()).isEqualTo("01012341234")
        );
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void deleteById() {
        // given
        long id = 1L;
        // when
        int affectedRows = customerDao.deleteById(id);
        final Optional<Customer> deletedCustomer = customerDao.findById(id);
        // then
        assertAll(
                () -> assertThat(affectedRows).isEqualTo(1),
                () -> assertThat(deletedCustomer.isEmpty()).isTrue()
        );
    }
}
