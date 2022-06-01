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
import woowacourse.shoppingcart.dto.CustomerInfoRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicateCheckResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerControllerTest {

    private final CustomerController customerController;

    @Autowired
    public CustomerControllerTest(CustomerController customerController) {
        this.customerController = customerController;
    }

    @DisplayName("이메일이 중복되는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"email@email.com, false", "distinctemail@email.com, true"})
    void checkDuplicateEmail(final String email, final boolean expected) {
        ResponseEntity<EmailDuplicateCheckResponse> response = customerController.checkDuplicateEmail(email);

        HttpStatus statusCode = response.getStatusCode();
        EmailDuplicateCheckResponse actual = Objects.requireNonNull(response.getBody());

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getSuccess()).isEqualTo(expected)
        );
    }

    @DisplayName("회원가입을 진행한다.")
    @Test
    void signUp() {
        String email = "newemail@email.com";
        String nickname = "쿼리치";
        String password = "password123!";

        CustomerRequest customerRequest = new CustomerRequest(email, nickname, password);

        ResponseEntity<CustomerResponse> response = customerController.signUp(customerRequest);

        HttpStatus statusCode = response.getStatusCode();
        CustomerResponse actual = Objects.requireNonNull(response.getBody());

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
        CustomerRequest customerRequest = new CustomerRequest(email, nickname, password);

        assertThatThrownBy(() -> customerController.signUp(customerRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("비밀번호를 확인한다.")
    @Test
    void checkPassword() {
        PasswordRequest passwordRequest = new PasswordRequest("password123!");

        ResponseEntity<Void> response = customerController.checkPassword("email@email.com", passwordRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("비밀번호를 확인에 실패한다.")
    @Test
    void checkInvalidPassword() {
        PasswordRequest passwordRequest = new PasswordRequest("password486!");

        assertThatThrownBy(() -> customerController.checkPassword("email@email.com", passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findCustomerInfo() {
        ResponseEntity<CustomerResponse> response = customerController.findCustomerInfo("email@email.com");

        HttpStatus statusCode = response.getStatusCode();
        CustomerResponse actual = Objects.requireNonNull(response.getBody());

        assertAll(
                () -> assertThat(statusCode).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getEmail()).isEqualTo("email@email.com"),
                () -> assertThat(actual.getNickname()).isEqualTo("파랑")
        );
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomerInfo() {
        CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest("파리채");

        ResponseEntity<Void> response = customerController.updateCustomerInfo("email@email.com", customerInfoRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("형식이 잘못된 닉네임으로 회원 정보를 수정하는 경우 예외가 발생한다.")
    @Test
    void updateInvalidCustomerInfo() {
        CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest("파리채채채채");

        assertThatThrownBy(() -> customerController.updateCustomerInfo("email@email.com", customerInfoRequest))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void updateCustomerPassword() {
        PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        ResponseEntity<Void> response = customerController.updateCustomerPassword("email@email.com", passwordRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @DisplayName("형식이 잘못된 비밀번호로 수정하는 경우 예외가 발생한다.")
    @Test
    void updateInvalidCustomerPassword() {
        PasswordRequest passwordRequest = new PasswordRequest("invalidpassword");

        assertThatThrownBy(() -> customerController.updateCustomerPassword("email@email.com", passwordRequest))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 비밀번호 형식입니다.");
    }
}