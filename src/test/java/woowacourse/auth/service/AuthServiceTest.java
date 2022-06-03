package woowacourse.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
    @DisplayName("회원 가입이 성공한다.")
    void registerCustomer() {
        CustomerResponse customerResponse = authService.register(firstCustomerRequest);

        assertAll(
                () -> assertThat(customerResponse.getId()).isNotNull(),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(firstCustomerEmail),
                () -> assertThat(customerResponse.getName()).isEqualTo(firstCustomerName),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(firstCustomerPhone),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(firstCustomerAddress)
        );
    }

    @Test
    @DisplayName("회원 로그인에 성공한다.")
    void loginCustomer_success() {
        // given
        CustomerResponse customerResponse = authService.register(firstCustomerRequest);
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
        CustomerResponse customerResponse = authService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword + "inv");
        // when
        // then
        assertThatThrownBy(() -> authService.login(tokenRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("Token을 활용해 회원 정보를 조회한다.")
    void findCustomerInformationByToken() {
        // given
        CustomerResponse createResponse = authService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        TokenResponse tokenResponse = authService.login(tokenRequest);
        Long id = getIdByTokenResponse(tokenResponse);
        // when
        CustomerResponse customer = authService.findCustomerById(id);
        // then
        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(createResponse.getId()),
                () -> assertThat(customer.getEmail()).isEqualTo(firstCustomerEmail),
                () -> assertThat(customer.getName()).isEqualTo(firstCustomerName),
                () -> assertThat(customer.getPhone()).isEqualTo(firstCustomerPhone),
                () -> assertThat(customer.getAddress()).isEqualTo(firstCustomerAddress)
        );
    }

    @Test
    @DisplayName("Token을 활용해 Customer 정보를 수정한다.")
    void editCustomer() {
        // given
        CustomerResponse createResponse = authService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        TokenResponse tokenResponse = authService.login(tokenRequest);
        String firstCustomerToken = tokenResponse.getAccessToken();
        CustomerRequest updateResponse = new CustomerRequest(firstCustomerEmail, firstCustomerPassword,
                firstCustomerName, "010-1111-1111", "경기도 양평시");
        Long id = getIdByTokenResponse(tokenResponse);
        // when
        authService.edit(id, updateResponse);
        // then
        CustomerResponse customer = authService.findCustomerById(id);
        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(createResponse.getId()),
                () -> assertThat(customer.getEmail()).isEqualTo(firstCustomerEmail),
                () -> assertThat(customer.getName()).isEqualTo(firstCustomerName),
                () -> assertThat(customer.getPhone()).isEqualTo("010-1111-1111"),
                () -> assertThat(customer.getAddress()).isEqualTo("경기도 양평시")
        );
    }

    @Test
    @DisplayName("토큰을 활용해 회원 정보를 삭제한다.")
    void deleteCustomer() {
        // given
        authService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        TokenResponse tokenResponse = authService.login(tokenRequest);
        Long id = getIdByTokenResponse(tokenResponse);
        // when & then
        assertDoesNotThrow(() -> authService.delete(id));
    }

    @Test
    @DisplayName("중복된 이메일을 입력했다면 True를 반환한다.")
    void validateEmail() {
        authService.register(firstCustomerRequest);

        assertThat(authService.validateEmail(firstCustomerEmail)).isTrue();
    }

    private Long getIdByTokenResponse(TokenResponse tokenResponse) {
        return Long.parseLong(jwtTokenProvider.getPayload(tokenResponse.getAccessToken()));
    }
}
