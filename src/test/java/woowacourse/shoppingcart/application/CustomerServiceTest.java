package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.customer.CustomerProfileResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.login.LoginRequest;
import woowacourse.shoppingcart.dto.login.LoginResponse;
import woowacourse.shoppingcart.dto.customer.SignUpRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerServiceTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerDao, jwtTokenProvider);
    }

    @DisplayName("중복된 아이디로 가입할 수 없다.")
    @Test
    void validateDuplicateUserId() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("puterism@woowacourse.com", "유콩", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @DisplayName("중복된 닉네임을 가입할 수 없다.")
    @Test
    void validateDuplicateNickname() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", "nickname1", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"asdf@woowacourse.com:1234asdf!", "david@woowacourse.com:12345asdf!"}, delimiter = ':')
    @DisplayName("존재하지 않은 회원 정보로 로그인하면 안된다.")
    void loginNonExistentCustomer(String userId, String password) {
        // given
        LoginRequest loginRequest = new LoginRequest(userId, password);

        // when & then
        assertThatThrownBy(() -> customerService.login(loginRequest))
                .isInstanceOf(LoginDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("로그인한다.")
    @Test
    void login() {
        // given
        LoginRequest loginRequest = new LoginRequest("puterism@woowacourse.com", "1234asdf!");

        // when
        LoginResponse loginResponse = customerService.login(loginRequest);

        // then
        assertAll(
                () -> assertThat(loginResponse.getAccessToken()).isNotNull(),
                () -> assertThat(loginResponse.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(loginResponse.getNickname()).isEqualTo("nickname")
        );
    }

    @DisplayName("가입하지 않은 사용자를 조회하면 안된다.")
    @Test
    void findByCustomerIdException() {
        // given
        TokenRequest tokenRequest = new TokenRequest("-1");

        // when & then
        assertThatThrownBy(() -> customerService.findProfile(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 사용자를 조회하려고 하면 안된다.")
    @Test
    void findByCustomerIdWithdrawalCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        customerService.withdraw(tokenRequest);

        // when & then
        assertThatThrownBy(() -> customerService.findProfile(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findByCustomerId() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));

        // when
        CustomerProfileResponse customerProfileResponse = customerService.findProfile(tokenRequest);

        // then
        assertAll(
                () -> assertThat(customerProfileResponse.getId()).isEqualTo(customerId),
                () -> assertThat(customerProfileResponse.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customerProfileResponse.getNickname()).isEqualTo("test")
        );
    }

    @DisplayName("존재하지 않는 사용자를 수정하려고 하면 안된다.")
    @Test
    void update() {
        // given
        TokenRequest tokenRequest = new TokenRequest("-1");
        CustomerUpdateProfileRequest customerUpdateProfileRequest = new CustomerUpdateProfileRequest("nickname");

        // when & then
        assertThatThrownBy(() -> customerService.updateProfile(tokenRequest, customerUpdateProfileRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("탈퇴한 사용자를 수정하려고 하면 안된다.")
    @Test
    void updateWithdrawalCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        customerService.withdraw(tokenRequest);

        // when & then
        CustomerUpdateProfileRequest customerUpdateProfileRequest = new CustomerUpdateProfileRequest("test2");
        assertThatThrownBy(() -> customerService.updateProfile(tokenRequest, customerUpdateProfileRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("존재하지 않는 사용자 비밀번호를 수정하려고 하면 안된다.")
    @Test
    void updatePassword() {
        // given
        TokenRequest tokenRequest = new TokenRequest("-1");
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234(dddd",
                "47374*ffff");

        // when & then
        assertThatThrownBy(() -> customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 사용자 비밀번호를 수정하려고 하면 안된다.")
    @Test
    void updatePasswordWithdrawalCustomer() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        customerService.withdraw(tokenRequest);
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!",
                "47374*ffff");

        // when & then
        assertThatThrownBy(() -> customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("회원 탈퇴를한다.")
    @Test
    void withdraw() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));

        // when
        customerService.withdraw(tokenRequest);

        // then
        assertThatThrownBy(() -> customerService.findProfile(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("존재하지 않은 회원이 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawNonCustomer() {
        // given
        TokenRequest tokenRequest = new TokenRequest("9999999");

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 회원이 다시 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawCustomerAgain() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));

        customerService.withdraw(tokenRequest);

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
