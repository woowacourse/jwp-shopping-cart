package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.DuplicatedCustomerEmailException;
import woowacourse.exception.InvalidTokenException;
import woowacourse.exception.WrongPasswordException;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRemoveRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwer1234!@#$";

    private final CustomerService customerService;

    CustomerServiceTest(final DataSource dataSource) {
        final CustomerDao customerDao = new CustomerDao(dataSource);
        this.customerService = new CustomerService(customerDao);
    }

    @DisplayName("회원을 가입한다.")
    @Test
    void registerCustomer() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        assertDoesNotThrow(() -> customerService.registerCustomer(customerRegisterRequest));
    }

    @DisplayName("중복된 이메일로 회원을 가입할 수 없다.")
    @Test
    void validateCustomerEmailNotDuplicated() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        customerService.registerCustomer(customerRegisterRequest);

        assertThatThrownBy(() -> customerService.registerCustomer(new CustomerRegisterRequest(
                CUSTOMER_EMAIL, "guest1", CUSTOMER_PASSWORD)))
                .isInstanceOf(DuplicatedCustomerEmailException.class);
    }

    @DisplayName("회원을 아이디로 조회한다.")
    @Test
    void findById() {
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);
        final Long customerId = customerService.registerCustomer(customerRegisterRequest);

        final CustomerResponse customerResponse = customerService.findById(customerId);

        assertThat(customerResponse)
                .extracting("email", "nickname")
                .containsExactly(CUSTOMER_EMAIL, CUSTOMER_NAME);
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomer() {
        final Long customerId = customerService.registerCustomer(
                new CustomerRegisterRequest(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        final String newNickname = "Guest123";
        final String newPassword = "qwer1234!@#$";
        customerService.updateCustomerNickName(customerId,
                new CustomerUpdateRequest(newNickname, CUSTOMER_PASSWORD, newPassword));

        CustomerResponse actual = customerService.findById(customerId);

        assertThat(actual.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("기존 비밀번호가 일치하지 않으면 회원 정보를 수정할 수 없다.")
    @Test
    void validatePasswordWhenUpdate() {
        final Long customerId = customerService.registerCustomer(
                new CustomerRegisterRequest(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        final String newNickname = "Guest123123";
        final String newPassword = "anotherqwer1234!@#$";

        assertThatThrownBy(() -> customerService.updateCustomerPassword(
                customerId, new CustomerUpdateRequest(newNickname, newPassword, newPassword)))
                .isInstanceOf(WrongPasswordException.class);
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void removeCustomer() {
        final Long customerId = customerService.registerCustomer(
                new CustomerRegisterRequest(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        customerService.removeCustomer(customerId, new CustomerRemoveRequest(CUSTOMER_PASSWORD));

        assertThatThrownBy(() -> customerService.findById(customerId))
                .isInstanceOf(InvalidTokenException.class);
    }
}
