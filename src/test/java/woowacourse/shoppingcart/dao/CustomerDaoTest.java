package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private static final Customer 회원 = new Customer(
            new Account("hamcheeseburger"),
            new Nickname("corinne"),
            new EncodedPassword("Password123!"),
            new Address("address"),
            new PhoneNumber("01012345678"));

    private final CustomerDao customerDao;

    public CustomerDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        // given

        // when
        final Customer savedCustomer = customerDao.save(회원);
        // then
        assertAll(
                () -> assertThat(savedCustomer.getId()).isEqualTo(2L),
                () -> assertThat(savedCustomer.getAccount().getValue()).isEqualTo("hamcheeseburger"),
                () -> assertThat(savedCustomer.getNickname().getValue()).isEqualTo("corinne"),
                () -> assertThat(savedCustomer.getPassword().getValue()).isEqualTo("Password123!"),
                () -> assertThat(savedCustomer.getAddress().getValue()).isEqualTo("address"),
                () -> assertThat(savedCustomer.getPhoneNumber().getValue()).isEqualTo("01012345678")
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
                () -> assertThat(foundCustomer.getAccount().getValue()).isEqualTo("pobi"),
                () -> assertThat(foundCustomer.getNickname().getValue()).isEqualTo("eden"),
                () -> assertThat(foundCustomer.getPassword().getValue()).isEqualTo("Password123!"),
                () -> assertThat(foundCustomer.getAddress().getValue()).isEqualTo("address"),
                () -> assertThat(foundCustomer.getPhoneNumber().getValue()).isEqualTo("01012345678")
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
                () -> assertThat(foundCustomer.getAccount().getValue()).isEqualTo("pobi"),
                () -> assertThat(foundCustomer.getNickname().getValue()).isEqualTo("eden"),
                () -> assertThat(foundCustomer.getPassword().getValue()).isEqualTo("Password123!"),
                () -> assertThat(foundCustomer.getAddress().getValue()).isEqualTo("address"),
                () -> assertThat(foundCustomer.getPhoneNumber().getValue()).isEqualTo("01012345678")
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
                () -> assertThat(actual.getNickname().getValue()).isEqualTo("eden22"),
                () -> assertThat(actual.getAddress().getValue()).isEqualTo("new address"),
                () -> assertThat(actual.getPhoneNumber().getValue()).isEqualTo("01012341234")
        );
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void deleteById() {
        // given
        final Customer savedCustomer = customerDao.save(회원);
        // when
        int affectedRows = customerDao.deleteById(savedCustomer.getId());
        final Optional<Customer> deletedCustomer = customerDao.findById(savedCustomer.getId());
        // then
        assertAll(
                () -> assertThat(affectedRows).isEqualTo(1),
                () -> assertThat(deletedCustomer.isEmpty()).isTrue()
        );
    }
}
