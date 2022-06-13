package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.exception.custum.DuplicatedValueException;
import woowacourse.shoppingcart.exception.custum.InvalidPasswordException;
import woowacourse.shoppingcart.exception.custum.ResourceNotFoundException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("회원가입을 하고 아이디로 회원을 찾는다.")
    @Test
    void signUpAndFindById() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jojo@naver.com", "jojogreen", "abcde123@");

        // when
        Long id = customerService.signUp(customerRequest);

        // then
        TokenRequest tokenRequest = new TokenRequest(id);
        CustomerResponse customerResponse = customerService.findById(tokenRequest);
        assertAll(
                () -> assertThat(customerResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }

    @DisplayName("username, password 로 로그인한 뒤 회원 정보를 반환한다.")
    @Test
    void login() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest("jo@naver.com", "abcde1234!");

        // when
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);

        // then
        assertAll(
                () -> assertThat(customerLoginResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerLoginResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }

    @DisplayName("회원 정보를 변경한다.")
    @Test
    void update() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        Long id = customerService.signUp(customerRequest);
        TokenRequest tokenRequest = new TokenRequest(id);
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("hunch");

        // when
        customerService.update(tokenRequest, customerUpdateRequest);

        // then
        CustomerResponse newCustomer = customerService.findById(tokenRequest);
        assertAll(
                () -> assertThat(newCustomer.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(newCustomer.getNickname())
                        .isEqualTo(customerUpdateRequest.getNickname())
        );
    }

    @DisplayName("회원 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        Long id = customerService.signUp(customerRequest);
        TokenRequest tokenRequest = new TokenRequest(id);
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("abcde1234!", "1234abcd@");

        // when
        customerService.updatePassword(tokenRequest, passwordChangeRequest);

        // then
        CustomerLoginRequest customerLoginRequest =
                new CustomerLoginRequest(customerRequest.getUserId(), passwordChangeRequest.getNewPassword());
        assertDoesNotThrow(() -> customerService.login(customerLoginRequest));
    }

    @DisplayName("토큰을 통해 회원탈퇴한다.")
    @Test
    void withdraw() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(
                "jo@naver.com", "abcde1234!");

        // when
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);
        TokenRequest request = new TokenRequest(customerLoginResponse.getId());
        customerService.withdraw(request);

        // then
        assertThatThrownBy(() -> customerService.findById(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 데이터입니다.");
    }

    @DisplayName("중복된 로그인용 아이디가 있는지 검사한다.")
    @Test
    void checkDuplicateUsername() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);

        // when
        customerService.checkDuplicateUsername("hunch@naver.com");

        // then
        assertThatThrownBy(() -> customerService.checkDuplicateUsername("jo@naver.com"))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessage("중복된 값이 존재합니다.");
    }

    @DisplayName("중복된 닉네임이 있는지 검사한다.")
    @Test
    void checkDuplicateNickname() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);

        // when
        customerService.checkDuplicateNickname("hunch");

        // then
        assertThatThrownBy(() -> customerService.checkDuplicateNickname("jojogreen"))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessage("중복된 값이 존재합니다.");
    }

    @DisplayName("해당 토큰의 유저의 패스워드가 요청값과 일치하는지 검사한다")
    @Test
    void matchPassword() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde1234!");
        customerService.signUp(customerRequest);
        CustomerLoginRequest customerLoginRequest = new CustomerLoginRequest(
                "jo@naver.com", "abcde1234!");

        // when
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginRequest);
        TokenRequest request = new TokenRequest(customerLoginResponse.getId());
        customerService.matchPassword(request, new PasswordRequest("abcde1234!"));

        // then
        assertThatThrownBy(() -> customerService.matchPassword(request, new PasswordRequest("abcdef1234!")))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
