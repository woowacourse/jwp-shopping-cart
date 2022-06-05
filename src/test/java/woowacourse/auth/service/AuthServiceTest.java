package woowacourse.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDao customerDao;

    private String firstCustomerEmail = "test@test.com";
    private String firstCustomerName = "Bunny";
    private String firstCustomerPhone = "010-0000-0000";
    private String firstCustomerAddress = "서울시 종로구";
    private String firstCustomerPassword = "Bunny1234!@";
    private CustomerRequest firstCustomerRequest;

    @BeforeEach
    void setUp() {
        firstCustomerRequest = new CustomerRequest(firstCustomerEmail,
                firstCustomerPassword,
                firstCustomerName,
                firstCustomerPhone,
                firstCustomerAddress);
    }

    @Test
    @DisplayName("회원 로그인에 성공한다.")
    void loginCustomer_success() {
        // given
        CustomerResponse customerResponse = customerService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        // when
        TokenResponse tokenResponse = authService.login(tokenRequest);
        // then
        assertThat(tokenResponse).isNotNull();
    }

    @Test
    @DisplayName("회원 정보에 존재하지 않는 이메일을 통한 로그인으로 회원 로그인에 실패한다.")
    void loginCustomer_fail_noExistEmail() {
        // given
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        // when
        // then
        assertThatThrownBy(() -> authService.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("존재하지 않는 이메일 입니다.");
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호를 통한 로그인으로 회원 로그인에 실패한다.")
    void loginCustomer_fail_InvalidPassword() {
        // given
        CustomerResponse customerResponse = customerService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword + "inv");
        // then
        assertThatThrownBy(() -> authService.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
