package woowacourse.shoppingcart.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.JWT_TOKEN_PROVIDER;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import woowacourse.shoppingcart.auth.application.dto.request.TokenRequest;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:truncate.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuthServiceTest {

    private final AuthService authService;
    private final CustomerService customerService;

    public AuthServiceTest(final DataSource dataSource) {
        final CustomerDao customerDao = new CustomerDao(dataSource);
        this.authService = new AuthService(customerDao, JWT_TOKEN_PROVIDER);
        this.customerService = new CustomerService(customerDao);
    }

    @BeforeEach
    void setUp() {
        customerService.registerCustomer(new CustomerRegisterRequest(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE));
    }

    @DisplayName("로그인을 한다.")
    @Test
    void loginSuccess() {
        final TokenResponse response = authService.login(new TokenRequest(EMAIL_VALUE, PASSWORD_VALUE));

        assertAll(
                () -> assertThat(response.getAccessToken()).isNotNull(),
                () -> assertThat(response.getNickname()).isEqualTo(NICKNAME_VALUE));
    }

    @DisplayName("존재하지 않는 이메일로 로그인을 한다.")
    @Test
    void loginWithNonExistentEmail() {
        final TokenRequest request = new TokenRequest("wrong" + EMAIL_VALUE, PASSWORD_VALUE);
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD);
    }

    @DisplayName("일치하지 않는 비밀번호로 로그인을 한다.")
    @Test
    void loginWithMismatchedPassword() {
        final TokenRequest request = new TokenRequest(EMAIL_VALUE, "wrong" + PASSWORD_VALUE);
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.MISMATCH_EMAIL_OR_PASSWORD);
    }
}