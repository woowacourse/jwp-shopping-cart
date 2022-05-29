package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CustomerDao customerDao;

    @Test
    void save() {
        customerDao = new CustomerDao(jdbcTemplate);
        final String email = "leo@naver.com";
        final String name = "leo";
        final String phone = "010-1111-1111";
        final String address = "서울시 종로구 숭인동";
        final String password = "password";
        final Customer customer = new Customer(email, name, phone, address, password);
        final Customer savedCustomer = customerDao.save(customer);

        assertThat(savedCustomer.getId()).isEqualTo(1L);
    }
}
