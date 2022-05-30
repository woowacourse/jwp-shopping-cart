package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        final Customer customer = new Customer("hamcheeseburger", "corinne", "password123", "address", "01012345678");
        // when
        final Customer savedCustomer = customerDao.save(customer);
        // then
        assertAll(
                () -> assertThat(savedCustomer.getId()).isEqualTo(2L),
                () -> assertThat(savedCustomer.getAccount()).isEqualTo("hamcheeseburger"),
                () -> assertThat(savedCustomer.getNickname()).isEqualTo("corinne"),
                () -> assertThat(savedCustomer.getPassword()).isEqualTo("password123"),
                () -> assertThat(savedCustomer.getAddress()).isEqualTo("address"),
                () -> assertThat(savedCustomer.getPhoneNumber()).isEqualTo("01012345678")
        );
    }
}
