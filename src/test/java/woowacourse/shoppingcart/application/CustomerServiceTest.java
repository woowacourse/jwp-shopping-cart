package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.LoginRequest;
import woowacourse.shoppingcart.dto.LoginResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerDao customerDao;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(authService, customerDao);
    }

    @DisplayName("아이디에 null 을 입력하면 안된다.")
    @Test
    void signUpUserIdNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(null, "유콩", "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 안된다.")
    void signUpUserIdBlankException(String userId) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(userId, "유콩", "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 안된다.")
    @Test
    void signUpNicknamedNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", null, "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 안된다.")
    void signUpNicknameBlankException(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", nickname, "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void signUpPasswordNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", "유콩", null);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void signUpPasswordBlankException(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", "유콩", password);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
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

    @DisplayName("아이디는 이메일 형식이 아니면 안된다.")
    @Test
    void validateUserIdFormat() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim", "nickname1", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("아이디는 이메일 형식으로 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa", "!@#$"})
    @DisplayName("닉네임이 영문, 한글, 숫자를 조합하여 2 ~ 10 자가 아니면 안된다.")
    void validateNicknameFormat(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", nickname, "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678!", "asdsad^f#$", "1231234ads", "asd2$$", "adsfsdaf324234#@$#@$#@"})
    @DisplayName("비밀번호가 영문, 한글, 숫자를 필수로 조합한 8 ~ 16 자가 아니면 안된다.")
    void validatePasswordFormat(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", "유콩", password);

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("비밀번호는 영문, 한글, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.");
    }

    @ParameterizedTest
    @CsvSource(value = {"asdf@woowacourse.com:1234asdf!", "puterism@woowacourse.com:12345asdf!"}, delimiter = ':')
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
                () -> assertThat(loginResponse.getToken()).isNotNull(),
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
        assertThatThrownBy(() -> customerService.findByCustomerId(tokenRequest))
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
        assertThatThrownBy(() -> customerService.findByCustomerId(tokenRequest))
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
        CustomerResponse customerResponse = customerService.findByCustomerId(tokenRequest);

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
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("nickname");

        // when & then
        assertThatThrownBy(() -> customerService.update(tokenRequest, customerUpdateRequest))
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
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("test2");
        assertThatThrownBy(() -> customerService.update(tokenRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사용자 정보 수정 시 닉네임에 null 을 입력하면 안된다.")
    @Test
    void updateNicknamedNullException() {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(null);

        // when & than
        assertThatThrownBy(() -> customerService.update(tokenRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 정보 수정 시 닉네임에 빈값을 입력하면 안된다.")
    void updateNicknameBlankException(String nickname) {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(nickname);

        // when & than
        assertThatThrownBy(() -> customerService.update(tokenRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("존재하지 않는 사용자 비밀번호를 수정하려고 하면 안된다.")
    @Test
    void updatePassword() {
        // given
        TokenRequest tokenRequest = new TokenRequest("-1");
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234(dddd", "47374*ffff");

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
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", "47374*ffff");

        // when & then
        assertThatThrownBy(() -> customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사용자 비밀번호 수정 시 닉네임에 null 을 입력하면 안된다.")
    @Test
    void updatePasswordNullException() {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", null);

        // when & than
        assertThatThrownBy(() -> customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 비밀번호 수정 시 닉네임에 빈값을 입력하면 안된다.")
    void updatePasswordBlankException(String password) {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        TokenRequest tokenRequest = new TokenRequest(String.valueOf(customerId));
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", password);

        // when & than
        assertThatThrownBy(() -> customerService.updatePassword(tokenRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
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
        assertThatThrownBy(() -> customerService.findByCustomerId(tokenRequest))
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
