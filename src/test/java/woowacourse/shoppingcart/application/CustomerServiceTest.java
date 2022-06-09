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
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.customer.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.customer.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerPasswordRequest;
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
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("puterism@woowacourse.com", "유콩",
                "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(customerSignUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

    @DisplayName("중복된 닉네임을 가입할 수 없다.")
    @Test
    void validateDuplicateNickname() {
        // given
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("coobim@woowacourse.com", "nickname1",
                "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(customerSignUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"asdf@woowacourse.com:1234asdf!", "david@woowacourse.com:12345asdf!"}, delimiter = ':')
    @DisplayName("존재하지 않은 회원 정보로 로그인하면 안된다.")
    void loginNonExistentCustomer(String userId, String password) {
        // given
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(userId, password);

        // when & then
        assertThatThrownBy(() -> customerService.login(customerLoginRequest))
                .isInstanceOf(LoginDataNotFoundException.class)
                .hasMessage("아아디 또는 비밀번호를 확인하여주세요.");
    }

    @DisplayName("로그인한다.")
    @Test
    void login() {
        // given
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest("puterism@woowacourse.com", "1234asdf!");

        // when
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);

        // then
        assertAll(
                () -> assertThat(customerLoginResponse.getAccessToken()).isNotNull(),
                () -> assertThat(customerLoginResponse.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customerLoginResponse.getNickname()).isEqualTo("nickname")
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
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");
        customerService.withdraw(tokenRequest, customerPasswordRequest);

        // when & then
        assertThatThrownBy(() -> customerService.findProfile(tokenRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findByCustomerId() {
        // given
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));

        // when
        CustomerResponse customerResponse = customerService.findProfile(tokenRequest);

        // then
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(customerId),
                () -> assertThat(customerResponse.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("test")
        );
    }

    @DisplayName("존재하지 않는 사용자를 수정하려고 하면 안된다.")
    @Test
    void update() {
        // given
        TokenRequest tokenRequest = new TokenRequest("-1");
        CustomerUpdateProfileRequest customerUpdateProfileRequest = new CustomerUpdateProfileRequest("nickname",
                "1243#adsfs");

        // when & then
        assertThatThrownBy(() -> customerService.updateProfile(tokenRequest, customerUpdateProfileRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("탈퇴한 사용자를 수정하려고 하면 안된다.")
    @Test
    void updateWithdrawalCustomer() {
        // given
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");
        customerService.withdraw(tokenRequest, customerPasswordRequest);

        // when & then
        CustomerUpdateProfileRequest customerUpdateProfileRequest = new CustomerUpdateProfileRequest("test2",
                "1234asdf!");
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
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");
        customerService.withdraw(tokenRequest, customerPasswordRequest);
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
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");

        // when
        customerService.withdraw(tokenRequest, customerPasswordRequest);

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
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(tokenRequest, customerPasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 회원이 다시 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawCustomerAgain() {
        // given
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234asdf!");
        customerService.withdraw(tokenRequest, customerPasswordRequest);

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(tokenRequest, customerPasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("회원탈퇴시 입력한 비밀번호가 실제 비밀번호와 일치하지 않으면 예외가 발생한다.")
    @Test
    void withdrawCustomerPasswordNotMatch() {
        // given
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest("test@woowacourse.com", "test",
                "1234asdf!");
        Long customerId = customerService.signUp(customerSignUpRequest);
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("1234affff!");

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(tokenRequest, customerPasswordRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아아디 또는 비밀번호를 확인하여주세요.");
    }
}
