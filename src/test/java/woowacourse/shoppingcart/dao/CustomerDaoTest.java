package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.global.DaoTest;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerDaoTest extends DaoTest {

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
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("email로 Customer를 조회한다.")
    @Test
    void findByEmail() {
        // when
        Customer customer = customerDao.findByEmail("puterism@naver.com").orElse(null);

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("email과 password로 Customer를 조회한다.")
    @Test
    void findByEmailAndPassword() {
        // when
        Customer customer = customerDao.findByEmailAndPassword("puterism@naver.com",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5").orElse(null);

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

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
        customerDao.deleteById(savedId, "12345678");
        Optional<Customer> result = customerDao.findById(savedId);

        // then
        assertThat(result).isEmpty();
    }
}
