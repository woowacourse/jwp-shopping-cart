package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerDaoTest {

    private final CustomerDao customerDao;

    public CustomerDaoTest(DataSource dataSource,
                           NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(dataSource, jdbcTemplate);
    }

    @DisplayName("Customer 를 저장한다.")
    @Test
    void save() {
        // given
        Customer customer = new Customer("roma@naver.com", "roma", "12345678");

        // when
        Long savedId = customerDao.save(customer);
        Long expected = customerDao.findIdByUserName("roma");

        // then
        assertThat(savedId).isEqualTo(expected);
    }

    @DisplayName("username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTest() {
        // given
        final String userName = "puterism";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(1L);
    }

    @DisplayName("대소문자를 구별하지 않고 username을 통해 아이디를 찾으면, id를 반환한다.")
    @Test
    void findIdByUserNameTestIgnoreUpperLowerCase() {
        // given
        final String userName = "SUNhpark42";

        // when
        final Long customerId = customerDao.findIdByUserName(userName);

        // then
        assertThat(customerId).isEqualTo(3L);
    }

    @DisplayName("id로 Customer를 조회한다.")
    @Test
    void findById() {
        // when
        Customer customer = customerDao.findById(1L).orElse(null);

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism", "12349053145");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("Customer 를 수정한다.")
    @Test
    void update() {
        // given
        Customer customer = new Customer("roma@naver.com", "roma", "12345678");

        // when
        Long savedId = customerDao.save(customer);
        customerDao.update(savedId, "philz");
        Customer result = customerDao.findById(savedId).orElse(null);

        // then
        assertThat(result.getUsername()).isEqualTo("philz");
    }

    @DisplayName("Customer 를 삭제한다.")
    @Test
    void delete() {
        // given
        Customer customer = new Customer("roma@naver.com", "roma", "12345678");

        // when
        Long savedId = customerDao.save(customer);
        customerDao.delete(savedId);
        Optional<Customer> result = customerDao.findById(savedId);

        // then
        assertThat(result).isEmpty();
    }
}
