package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.PasswordRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.application.dto.request.LoginRequest;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;
import woowacourse.shoppingcart.domain.Encryption.EncryptionStrategy;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;
import woowacourse.shoppingcart.exception.duplicateddata.CustomerDuplicatedDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql", "classpath:customer.sql"})
@DisplayName("Customer 서비스 테스트")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("아이디에 null 을 입력하면 예외가 발생한다.")
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
    @DisplayName("아이디에 빈값을 입력하면 예외가 발생한다.")
    void signUpUserIdBlankException(String userId) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(userId, "유콩", "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 예외가 발생한다.")
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
    @DisplayName("닉네임에 빈값을 입력하면 예외가 발생한다.")
    void signUpNicknameBlankException(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", nickname, "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 예외가 발생한다.")
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
    @DisplayName("비밀번호에 빈값을 입력하면 예외가 발생한다.")
    void signUpPasswordBlankException(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username@woowacourse.com", "유콩", password);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @DisplayName("중복된 아이디로 가입하면 예외가 발생한다.")
    @Test
    void validateDuplicateUserIdException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("puterism@woowacourse.com", "유콩", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @DisplayName("중복된 닉네임으로 가입하면 예외가 발생한다.")
    @Test
    void validateDuplicateNicknameException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", "nickname1", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("아이디는 이메일 형식이 아니면 예외가 발생한다.")
    @Test
    void validateUserIdFormatException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim", "nickname1", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("아이디는 이메일 형식으로 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa", "!@#$"})
    @DisplayName("닉네임이 영문, 한글, 숫자를 조합하여 2 ~ 10 자가 아니면 예외가 발생한다.")
    void validateNicknameFormatException(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", nickname, "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678!", "asdsad^f#$", "1231234ads", "asd2$$", "adsfsdaf324234#@$#@$#@"})
    @DisplayName("비밀번호가 영문, 한글, 숫자를 필수로 조합한 8 ~ 16 자가 아니면 예외가 발생한다.")
    void validatePasswordFormatException(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("coobim@woowacourse.com", "유콩", password);

        // when & then
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.");
    }

    @DisplayName("존재하지 않는 아이디로 로그인하면 예외가 발생한다.")
    @Test
    void loginNonExistentCustomerException() {
        // given
        LoginRequest loginRequest = new LoginRequest("asdf@woowacourse.com", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.login(loginRequest))
                .isInstanceOf(LoginDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("틀린 비밀번호로 로그인하면 예외가 발생한다.")
    @Test
    void loginNotEqualsPasswordException() {
        // given
        LoginRequest loginRequest = new LoginRequest("puterism@woowacourse.com", "12345asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.login(loginRequest))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("로그인한다.")
    @Test
    void login() {
        // given
        LoginRequest loginRequest = new LoginRequest("puterism@woowacourse.com", "1234asdf!");

        // when
        CustomerResponse customerResponse = customerService.login(loginRequest);

        // then
        assertAll(
                () -> assertThat(customerResponse.getUserId()).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("nickname")
        );
    }

    @DisplayName("가입하지 않은 사용자를 조회하면 예외가 발생한다.")
    @Test
    void findByCustomerIdNotExistingException() {
        // given
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest("-1");

        // when & then
        assertThatThrownBy(() -> customerService.findByCustomerId(customerIdentificationRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 사용자를 조회하려고 하면 예외가 발생한다.")
    @Test
    void findByCustomerIdWithdrawalCustomerException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!"));

        // when & then
        assertThatThrownBy(() -> customerService.findByCustomerId(customerIdentificationRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findByCustomerId() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        // when
        CustomerResponse customerResponse = customerService.findByCustomerId(customerIdentificationRequest);

        // then
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(customerId),
                () -> assertThat(customerResponse.getUserId()).isEqualTo("test@woowacourse.com"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("test")
        );
    }

    @DisplayName("존재하지 않는 사용자를 수정하려고 하면 예외가 발생한다.")
    @Test
    void updateNotExistingException() {
        // given
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest("-1");
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("test", "1234asdf!");

        // when & then
        assertThatThrownBy(() -> customerService.update(customerIdentificationRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 사용자를 수정하려고 하면 예외가 발생한다.")
    @Test
    void updateWithdrawalCustomerException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!"));

        // when & then
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("test2", "1234asdf!");
        assertThatThrownBy(() -> customerService.update(customerIdentificationRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사용자 정보 수정 시 닉네임에 null 을 입력하면 예외가 발생한다.")
    @Test
    void updateNicknamedNullException() {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(null, "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.update(customerIdentificationRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 정보 수정 시 닉네임에 빈값을 입력하면 예외가 발생한다.")
    void updateNicknameBlankException(String nickname) {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(nickname, "1234asdf!");

        // when & than
        assertThatThrownBy(() -> customerService.update(customerIdentificationRequest, customerUpdateRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("회원정보 수정 시 본인 계정의 비밀번호가 아닌 값을 입력하면 예외가 발생한다.")
    @Test
    void updateNicknameInvalidPasswordException() {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("test2", "invalidPassword");

        // when & then
        assertThatThrownBy(() -> customerService.update(customerIdentificationRequest, customerUpdateRequest))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("존재하지 않는 사용자 비밀번호를 수정하려고 하면 예외가 발생한다.")
    @Test
    void updatePasswordNotExistingException() {
        // given
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest("-1");
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234(dddd", "47374*ffff");

        // when & then
        assertThatThrownBy(() -> customerService.updatePassword(customerIdentificationRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 사용자 비밀번호를 수정하려고 하면 예외가 발생한다.")
    @Test
    void updatePasswordWithdrawalCustomerException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!"));
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", "47374*ffff");

        // when & then
        assertThatThrownBy(() -> customerService.updatePassword(customerIdentificationRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사용자 비밀번호 수정 시 닉네임에 null 을 입력하면 예외가 발생한다.")
    @Test
    void updatePasswordNullException() {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", null);

        // when & than
        assertThatThrownBy(() -> customerService.updatePassword(customerIdentificationRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 비밀번호 수정 시 닉네임에 빈값을 입력하면 예외가 발생한다.")
    void updatePasswordBlankException(String password) {
        // given
        Long customerId = customerService.signUp(new SignUpRequest("test@woowacourse.com", "test", "1234asdf!"));
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerUpdatePasswordRequest customerUpdatePasswordRequest = new CustomerUpdatePasswordRequest("1234asdf!", password);

        // when & than
        assertThatThrownBy(() -> customerService.updatePassword(customerIdentificationRequest, customerUpdatePasswordRequest))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @DisplayName("회원 탈퇴를한다.")
    @Test
    void withdraw() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        // when
        customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!"));

        // then
        assertThatThrownBy(() -> customerService.findByCustomerId(customerIdentificationRequest))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("존재하지 않은 회원이 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawNonCustomerException() {
        // given
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest("9999999");

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!")))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("탈퇴한 회원이 다시 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawCustomerAgainException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!"));

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(customerIdentificationRequest, new PasswordRequest("1234asdf!")))
                .isInstanceOf(CustomerDataNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("틀린 비밀번호로 탈퇴하면 예외가 발생한다.")
    @Test
    void withdrawInvalidPasswordException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        // when & then
        assertThatThrownBy(() -> customerService.withdraw(customerIdentificationRequest, new PasswordRequest("invalidPassword")))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("중복되는 아이디가 없는 것을 확인한다.")
    @Test
    void validateDuplicatedUserId() {
        // when & then
        assertThatCode(() -> customerService.validateDuplicateUserId("newUserId@woowacourse.com"))
                .doesNotThrowAnyException();
    }

    @DisplayName("중복되는 아이디가 있을 경우 예외가 발생한다.")
    @Test
    void validateDuplicatedUserIdException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerResponse customerResponse = customerService.findByCustomerId(customerIdentificationRequest);

        // when & then
        assertThatCode(() -> customerService.validateDuplicateUserId(customerResponse.getUserId()))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @DisplayName("중복되는 닉네임이 없는 것을 확인한다.")
    @Test
    void validateDuplicatedNickname() {
        // when & then
        assertThatCode(() -> customerService.validateDuplicateNickname("newNickname"))
                .doesNotThrowAnyException();
    }

    @DisplayName("중복되는 닉네임이 있을 경우 예외가 발생한다.")
    @Test
    void validateDuplicatedNicknameException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        CustomerResponse customerResponse = customerService.findByCustomerId(customerIdentificationRequest);

        // when & then
        assertThatCode(() -> customerService.validateDuplicateNickname(customerResponse.getNickname()))
                .isInstanceOf(CustomerDuplicatedDataException.class)
                .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @DisplayName("비밀번호가 일치하는 것을 확인한다.")
    @Test
    void matchPassword() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        PasswordRequest passwordRequest = new PasswordRequest("1234asdf!");

        // when & then
        assertThatCode(() -> customerService.matchPassword(customerIdentificationRequest, passwordRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void matchingInvalidPasswordException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));
        PasswordRequest passwordRequest = new PasswordRequest("invalidPassword");

        // when & then
        assertThatCode(() -> customerService.matchPassword(customerIdentificationRequest, passwordRequest))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
