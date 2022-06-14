package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.notfound.InvalidCustomerException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerServiceTest {

    private final CustomerDao customerDao;
    private final CustomerService customerService;

    public CustomerServiceTest(DataSource dataSource,
                               NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(dataSource, jdbcTemplate);
        CustomerSpecification customerSpecification = new CustomerSpecification(customerDao);
        customerService = new CustomerService(customerDao, customerSpecification);
    }

    @DisplayName("Customer 를 저장한다.")
    @Test
    void save() {
        // given
        CustomerCreateRequest createRequest = CustomerCreateRequest.from("roma@naver.com", "roma", "12345678");

        // when
        Long savedId = customerService.save(createRequest);
        Customer customer = customerDao.findById(savedId).orElse(null);

        // then
        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createRequest.toEntity());
    }

    @DisplayName("id로 Customer를 조회한다.")
    @Test
    void findById() {
        // when
        CustomerResponse response = customerService.findByIdForUpdateView(1L);

        // then
        CustomerResponse expected = CustomerResponse.from(
                new Customer(1L, "puterism@naver.com", "puterism",
                        "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5"));

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("email로 Customer를 조회한다.")
    @Test
    void findByEmail() {
        // when
        Customer customer = customerService.findByEmail("puterism@naver.com");

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("email, password로 Customer를 조회한다.")
    @Test
    void findByEmailAndPassword() {
        // when
        Customer customer = customerService.findByEmailAndPassword("puterism@naver.com",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                "e3ca6327a41d28aa4b31f9901c799fcd047eb31773f7fcc9bd33f2795745dde5");

        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 id로 Customer를 조회하면 예외를 발생시킨다.")
    @Test
    void findById_throwNotExistId() {
        // when then
        assertThatThrownBy(() -> customerService.findByIdForUpdateView(100L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("존재하지 않는 email로 Customer를 조회하면 예외를 발생시킨다.")
    @Test
    void findByEmail_throwNotExistId() {
        // when then
        assertThatThrownBy(() -> customerService.findByEmail("rorororo@naver.com"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("존재하지 않는 email 혹은 password로 Customer를 조회하면 예외를 발생시킨다.")
    @ParameterizedTest
    @CsvSource(value = {
            "failemail@naver.com:12349053145",
            "puterism@naver.com:failpassword",
            "failemail@naver.com:failpassword"}, delimiter = ':')
    void findByEmailAndPassword_throwNotExistId(String email, String password) {
        // when then
        assertThatThrownBy(() -> customerService.findByEmailAndPassword(email, password))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("Customer 를 수정한다.")
    @Test
    void update() {
        // given
        CustomerCreateRequest createRequest = CustomerCreateRequest.from("roma@naver.com", "roma", "12345678");
        Long savedId = customerService.save(createRequest);

        // when
        customerService.update(savedId, CustomerUpdateRequest.from("philz"));
        Customer result = customerDao.findById(savedId).orElse(null);

        // then
        assertThat(result.getUsername()).isEqualTo("philz");
    }

    @DisplayName("Customer 를 삭제한다.")
    @Test
    void delete() {
        // given
        CustomerCreateRequest createRequest = CustomerCreateRequest.from("roma@naver.com", "roma", "12345678");
        Long savedId = customerService.save(createRequest);

        // when
        customerService.delete(savedId, "12345678");

        // then
        assertThatThrownBy(() -> customerService.findByIdForUpdateView(savedId))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }
}
