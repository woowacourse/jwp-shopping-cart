package woowacourse.shoppingcart.customer.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRemoveRequest;
import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:truncate.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest(final DataSource dataSource) {
        final CustomerDao customerDao = new CustomerDao(dataSource);
        this.customerService = new CustomerService(customerDao);
    }

    private Long registerCustomer(final String email, final String nickname, final String password) {
        final CustomerRegisterRequest request = new CustomerRegisterRequest(email, nickname, password);
        return customerService.registerCustomer(request);
    }

    @DisplayName("회원을 등록한다.")
    @Test
    void registerCustomer() {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        assertThat(customerId).isPositive();
    }

    @DisplayName("중복된 이메일로 회원을 등록한다.")
    @Test
    void registerCustomerWithDuplicatedEmail() {
        registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);

        assertThatThrownBy(() -> registerCustomer(EMAIL_VALUE, "new" + NICKNAME_VALUE, "new" + PASSWORD_VALUE))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.ALREADY_EMAIL_EXIST);
    }

    @DisplayName("아이디로 회원을 조회한다.")
    @Test
    void findById() {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);

        assertThat(customerService.findById(customerId))
                .extracting("email", "nickname")
                .containsExactly(EMAIL_VALUE, NICKNAME_VALUE);
    }

    @DisplayName("존재하지 않는 아이디로 회원을 조회한다.")
    @Test
    void findByIdWithNonExistentId() {
        assertThatThrownBy(() -> customerService.findById(0L))
                .isInstanceOf(AuthException.class)
                .extracting("exceptionCode")
                .isEqualTo(AuthExceptionCode.REQUIRED_AUTHORIZATION);
    }

    @DisplayName("회원의 닉네임을 수정한다.")
    @ParameterizedTest
    @ValueSource(strings = {"newNick"})
    void updateCustomerProfile(final String newNickname) {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        customerService.updateCustomerProfile(customerId, new CustomerProfileUpdateRequest(newNickname));

        assertThat(customerService.findById(customerId))
                .extracting("nickname")
                .isEqualTo(newNickname);
    }

    @DisplayName("존재하지 않는 회원의 닉네임을 수정한다.")
    @Test
    void updateProfileOfNonExistentCustomer() {
        final CustomerProfileUpdateRequest request = new CustomerProfileUpdateRequest(NICKNAME_VALUE);
        assertThatThrownBy(() -> customerService.updateCustomerProfile(0L, request))
                .isInstanceOf(AuthException.class)
                .extracting("exceptionCode")
                .isEqualTo(AuthExceptionCode.REQUIRED_AUTHORIZATION);
    }

    @DisplayName("회원의 비밀번호를 수정한다.")
    @ParameterizedTest
    @ValueSource(strings = {"new"})
    void updateCustomerPassword(final String newPassword) {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        final CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest(PASSWORD_VALUE, PASSWORD_VALUE);
        assertDoesNotThrow(() -> customerService.updateCustomerPassword(customerId, request));
    }

    @DisplayName("기존과 일치하지 않는 비밀번호로 회원의 비밀번호를 수정한다.")
    @Test
    void updateCustomerPasswordWithMisMatchedPassword() {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        final CustomerPasswordUpdateRequest request =
                new CustomerPasswordUpdateRequest("Wrong" + PASSWORD_VALUE, PASSWORD_VALUE);
        assertThatThrownBy(() -> customerService.updateCustomerPassword(customerId, request))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.MISMATCH_PASSWORD);
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정한다.")
    @Test
    void updatePasswordOfNonExistentCustomer() {
        final CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest(PASSWORD_VALUE, PASSWORD_VALUE);
        assertThatThrownBy(() -> customerService.updateCustomerPassword(0L, request))
                .isInstanceOf(AuthException.class)
                .extracting("exceptionCode")
                .isEqualTo(AuthExceptionCode.REQUIRED_AUTHORIZATION);
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void removeCustomer() {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        final CustomerRemoveRequest request = new CustomerRemoveRequest(PASSWORD_VALUE);
        assertDoesNotThrow(() -> customerService.removeCustomer(customerId, request));
    }

    @DisplayName("존재하지 않는 회원을 삭제한다.")
    @Test
    void removeNonExistentCustomer() {
        final CustomerRemoveRequest request = new CustomerRemoveRequest(PASSWORD_VALUE);
        assertThatThrownBy(() -> customerService.removeCustomer(0L, request))
                .isInstanceOf(AuthException.class)
                .extracting("exceptionCode")
                .isEqualTo(AuthExceptionCode.REQUIRED_AUTHORIZATION);
    }

    @DisplayName("기존과 일치하지 않는 비밀번호로 회원을 삭제한다.")
    @Test
    void removeCustomerWithMisMatchedPassword() {
        final Long customerId = registerCustomer(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
        final CustomerRemoveRequest request = new CustomerRemoveRequest("Wrong" + PASSWORD_VALUE);
        assertThatThrownBy(() -> customerService.removeCustomer(customerId, request))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.MISMATCH_PASSWORD);
    }
}
