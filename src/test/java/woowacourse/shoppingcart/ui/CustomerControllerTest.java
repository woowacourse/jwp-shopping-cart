package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.customer.CustomerProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.EmailUniqueCheckResponse;
import woowacourse.shoppingcart.dto.customer.PasswordCheckResponse;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerControllerTest {

    public static final Email EMAIL = new Email("email@email.com");

    private final CustomerController customerController;

    @Autowired
    public CustomerControllerTest(final CustomerController customerController) {
        this.customerController = customerController;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void checkDuplicateEmail(final String email, final boolean expected) {
        final ResponseEntity<EmailUniqueCheckResponse> response =
                customerController.checkDuplicateEmail(new Email(email));

        final HttpStatus statusCode = response.getStatusCode();
        final EmailUniqueCheckResponse actual = Objects.requireNonNull(response.getBody());

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getUnique()).isEqualTo(expected)
        );
    }

    @DisplayName("회원가입을 진행한다.")
    @Test
    void signUp() {
        final String email = "newemail@email.com";
        final String nickname = "쿼리치";
        final String password = "password123!";

        final CustomerRequest customerRequest = new CustomerRequest(email, nickname, password);

        final ResponseEntity<CustomerResponse> response = customerController.signUp(customerRequest);

        final HttpStatus statusCode = response.getStatusCode();
        final CustomerResponse actual = Objects.requireNonNull(response.getBody());

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getNickname()).isEqualTo(nickname)
        );
    }

    @DisplayName("회원가입에 실패한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "잘못된 이메일, 파리채, password123!",
            "email@email.com, eng, password123!",
            "email@email.com, 파리채, asd123!"
    })
    void signUpFail(final String email, final String nickname, final String password) {
        final CustomerRequest customerRequest = new CustomerRequest(email, nickname, password);

        assertThatThrownBy(() -> customerController.signUp(customerRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("비밀번호를 확인한다.")
    @Test
    void checkPassword() {
        final PasswordRequest passwordRequest = new PasswordRequest("password123!");

        final ResponseEntity<PasswordCheckResponse> response = customerController.checkPassword(EMAIL, passwordRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("비밀번호를 확인에 실패한다.")
    @Test
    void checkInvalidPassword() {
        final PasswordRequest passwordRequest = new PasswordRequest("password486!");

        assertThatThrownBy(() -> customerController.checkPassword(EMAIL, passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findProfile() {
        final ResponseEntity<CustomerResponse> response = customerController.findProfile(EMAIL);

        final HttpStatus statusCode = response.getStatusCode();
        final CustomerResponse actual = Objects.requireNonNull(response.getBody());

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getEmail()).isEqualTo(EMAIL.getValue()),
                () -> assertThat(actual.getNickname()).isEqualTo("파랑")
        );
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateProfile() {
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채");

        final ResponseEntity<Void> response = customerController.updateProfile(EMAIL,
                customerProfileRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("형식이 잘못된 닉네임으로 회원 정보를 수정하는 경우 예외가 발생한다.")
    @Test
    void updateInvalidProfileFormat() {
        final CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("파리채채채채");

        assertThatThrownBy(() -> customerController.updateProfile(EMAIL, customerProfileRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        final PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        final ResponseEntity<Void> response = customerController.updatePassword(EMAIL,
                passwordRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("형식이 잘못된 비밀번호로 수정하는 경우 예외가 발생한다.")
    @Test
    void updateInvalidPassword() {
        final PasswordRequest passwordRequest = new PasswordRequest("invalidpassword");

        assertThatThrownBy(() -> customerController.updatePassword(EMAIL, passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 비밀번호 형식입니다.");
    }

    @DisplayName("탈퇴한다.")
    @Test
    void signOut() {
        final ResponseEntity<Void> response = customerController.signOut(EMAIL);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
