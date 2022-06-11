package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.TestConstant.PARAM_TEST_NAME;

import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
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
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.UnEncodedPassword;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.NotMatchPasswordException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CustomerServiceTest {

    private final CustomerService customerService;
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceTest(DataSource dataSource,
                               NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(dataSource, jdbcTemplate);
        passwordEncoder = new PasswordEncoder();
        customerService = new CustomerService(customerDao, passwordEncoder);
    }

    @DisplayName("Customer 를 저장한다.")
    @Test
    void save() {
        // given
        CustomerCreateRequest createRequest = new CustomerCreateRequest("roma@naver.com", "roma", "12345678");

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
        Customer customer = customerService.getById(1L);

        // then
        EncodedPassword encodedPassword = passwordEncoder.encode(new UnEncodedPassword("12349053145"));
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism", encodedPassword);

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expected);
    }

    @DisplayName("email로 Customer를 조회한다.")
    @Test
    void findByEmail() {
        // when
        Customer customer = customerService.getByEmail("puterism@naver.com");

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism",
                new EncodedPassword(customer.getPassword()));

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expected);
    }

    @DisplayName("email, password로 Customer를 조회한다.")
    @Test
    void findByEmailAndPassword() {
        // when
        EncodedPassword encodedPassword = passwordEncoder.encode(new UnEncodedPassword("12349053145"));
        Customer customer = customerService.getByEmailAndPassword("puterism@naver.com", encodedPassword.getValue());

        // then
        Customer expected = new Customer(1L, "puterism@naver.com", "puterism", encodedPassword);

        assertThat(customer).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 id로 Customer를 조회하면 예외를 발생시킨다.")
    @Test
    void findById_throwNotExistId() {
        // when then
        assertThatThrownBy(() -> customerService.getById(100L))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("존재하지 않는 email로 Customer를 조회하면 예외를 발생시킨다.")
    @Test
    void findByEmail_throwNotExistId() {
        // when then
        assertThatThrownBy(() -> customerService.getByEmail("rorororo@naver.com"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("존재하지 않는 email 혹은 password로 Customer를 조회하면 예외를 발생시킨다.")
    @ParameterizedTest(name = PARAM_TEST_NAME)
    @CsvSource(value = {
            "failemail@naver.com:12349053145",
            "puterism@naver.com:failpassword",
            "failemail@naver.com:failpassword"}, delimiter = ':')
    void findByEmailAndPassword_throwNotExistId(String email, String password) {
        // when then
        assertThatThrownBy(() -> customerService.getByEmailAndPassword(email, password))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("Customer 를 수정한다.")
    @Test
    void update() {
        // given
        CustomerCreateRequest createRequest = new CustomerCreateRequest("roma@naver.com", "roma", "12345678");
        Long savedId = customerService.save(createRequest);

        // when
        customerService.update(savedId, new CustomerUpdateRequest("philz"));
        Customer result = customerDao.findById(savedId).orElse(null);

        // then
        assert result != null;
        assertThat(result.getUsername()).isEqualTo("philz");
    }

    @DisplayName("Customer 를 삭제한다.")
    @Test
    void delete() {
        // given
        CustomerCreateRequest createRequest = new CustomerCreateRequest("roma@naver.com", "roma", "12345678");
        Long savedId = customerService.save(createRequest);

        // when
        customerService.delete(savedId);

        // then
        assertThatThrownBy(() -> customerService.getById(savedId))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @DisplayName("Password 일치하는지 여부를 판단한다.")
    @Test
    void checkSamePassword() {
        // given
        UnEncodedPassword enteredPassword = new UnEncodedPassword("123456789");
        EncodedPassword savedPassword = passwordEncoder.encode(enteredPassword);
        Customer customer = new Customer("hi@naver.com", "user", savedPassword);

        // when then
        Assertions.assertThatNoException()
                .isThrownBy(() -> customerService.checkSamePassword(customer, enteredPassword));
    }

    @DisplayName("checkSamePassword는 Password 일치하지 않으면 예외를 발생시킨다.")
    @Test
    void checkSamePassword_unmatch_password() {
        // given
        UnEncodedPassword enteredPassword = new UnEncodedPassword("123456789");
        EncodedPassword encode = passwordEncoder.encode(enteredPassword);
        Customer customer = new Customer("hi@naver.com", "user", encode);

        // when then
        Assertions.assertThatThrownBy(
                        () -> customerService.checkSamePassword(customer, new UnEncodedPassword("failPassword")))
                .isInstanceOf(NotMatchPasswordException.class);
    }
}
