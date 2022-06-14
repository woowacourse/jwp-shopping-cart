package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDetailResponse;
import woowacourse.shoppingcart.dto.customer.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;

@SpringBootTest
@Sql("classpath:resetTables.sql")
class CustomerControllerTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "12345678";

    @Autowired
    private CustomerController customerController;

    @DisplayName("회원가입을 한다.")
    @Test
    void register() {
        // given 이름, 이메일, 비밀번호를 입력하고
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);

        // when 회원 등록을 요청하면
        final ResponseEntity<Void> response = customerController.register(request);

        // then 회원이 성공적으로 등록된다.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @DisplayName("회원가입시 동일한 이메일이 존재하는 경우 예외가 발생한다.")
    @Test
    void register_withDuplicatedEmail_throwsException() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(request);

        // when, then
        assertThatThrownBy(() -> customerController.register(request))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("id를 통해 회원을 조회한다.")
    void showMyDetail() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(request);

        // when
        final ResponseEntity<CustomerDetailResponse> customerDetailResponse = customerController.showMyDetail(1L);
        final CustomerDetailResponse actual = customerDetailResponse.getBody();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new CustomerDetailResponse(NAME, EMAIL));
    }

    @Test
    @DisplayName("회원을 탈퇴한다.")
    void delete() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(request);

        final CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest(PASSWORD);

        // when
        final ResponseEntity<Void> response = customerController.delete(1L, customerDeleteRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    @DisplayName("회원 탈퇴시 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    void delete_passwordNotMatch_throwsException() {
        // given
        final CustomerRegisterRequest request = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(request);

        final CustomerDeleteRequest customerDeleteRequest = new CustomerDeleteRequest("1111111111");

        // when, then
        assertThatThrownBy(() -> customerController.delete(1L, customerDeleteRequest))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("회원의 정보를 수정한다.")
    void updateProfile() {
        // given
        final CustomerRegisterRequest registerRequest = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(registerRequest);

        final CustomerProfileUpdateRequest request = new CustomerProfileUpdateRequest("포비");

        // when
        final ResponseEntity<Void> response = customerController.updateProfile(1L, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("회원의 비밀번호를 수정한다.")
    void updatePassword() {
        // given
        final CustomerRegisterRequest registerRequest = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(registerRequest);

        final CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest(PASSWORD, "newpassword123");

        // when
        final ResponseEntity<Void> response = customerController.updatePassword(1L, request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("회원의 비밀번호 수정시 기존 비밀번호가 일치하지 않을 경우 예외가 발생한다.")
    void updatePassword_passwordNotMatch_throwsException() {
        // given
        final CustomerRegisterRequest registerRequest = new CustomerRegisterRequest(NAME, EMAIL, PASSWORD);
        customerController.register(registerRequest);

        final CustomerPasswordUpdateRequest request = new CustomerPasswordUpdateRequest("wrongpassword12ffd3",
                "newpassword123");

        // when, then
        assertThatThrownBy(() -> customerController.updatePassword(1L, request))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
