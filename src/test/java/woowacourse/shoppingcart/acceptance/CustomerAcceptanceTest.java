package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.RequestHandler.deleteRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.getRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.patchRequest;
import static woowacourse.shoppingcart.acceptance.RequestHandler.postRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.DuplicatedCustomerEmailException;
import woowacourse.exception.WrongPasswordException;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRemoveRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwer1234!@#$";

    @DisplayName("회원 가입에 성공하면 상태코드 201을 반환한다.")
    @Test
    void registerCustomer() {
        // given
        final CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD);

        // when
        final ExtractableResponse<Response> response = postRequest("/customers",
                customerRegisterRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertAll(() -> {
            assertThat(response.header("Location")).isEqualTo("/customers/1");
            assertThat(response.jsonPath().getObject(".", CustomerResponse.class))
                    .extracting("email", "nickname")
                    .containsExactly(CUSTOMER_EMAIL, CUSTOMER_NAME);
        });
    }

    @DisplayName("동일한 이메일로 회원 가입 요청시 상태코드 400을 반환한다.")
    @Test
    void registerCustomerWithDuplicatedEmail() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = postRequest("/customers",
                new CustomerRegisterRequest(CUSTOMER_EMAIL, "guest1", CUSTOMER_PASSWORD));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        final ExceptionResponse exceptionResponse = response.jsonPath().getObject(".", ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo(new DuplicatedCustomerEmailException().getMessage());
    }

    @DisplayName("개인정보를 조회한다.")
    @Test
    void findCustomer() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);

        final ExtractableResponse<Response> getResponse = getRequest(
                "/customers", tokenResponse.getAccessToken());

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.jsonPath().getObject(".", CustomerResponse.class))
                .extracting("email", "nickname")
                .containsExactly(CUSTOMER_EMAIL, CUSTOMER_NAME);
    }

    @DisplayName("정보를 수정한다.")
    @Test
    void updateMe() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);

        final CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                "newGuest", CUSTOMER_PASSWORD, "qwer1234!@#$");
        patchRequest("/customers/profile", customerUpdateRequest, tokenResponse.getAccessToken());
        ExtractableResponse<Response> getResponse = getRequest("/customers",
                tokenResponse.getAccessToken());
        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.jsonPath().getObject(".", CustomerResponse.class)
                .getNickname()).isEqualTo("newGuest");
    }

    @DisplayName("비밀번호가 틀리면 정보를 수정할 수 없다.")
    @Test
    void validatePassword() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));

        // when
        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));

        // then
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);
        final CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                "newGuest", "wrongqwe123!@#", "qwer1234!@#$");
        final ExtractableResponse<Response> patchResponse = patchRequest(
                "/customers/password", customerUpdateRequest, tokenResponse.getAccessToken());

        assertThat(patchResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        final ExceptionResponse exceptionResponse = patchResponse.jsonPath().getObject(".", ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo(new WrongPasswordException().getMessage());
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void removeCustomer() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));
        final CustomerRemoveRequest customerRemoveRequest = new CustomerRemoveRequest(CUSTOMER_PASSWORD);

        // when
        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);

        final ExtractableResponse<Response> deleteResponse = deleteRequest(
                "/customers", customerRemoveRequest, tokenResponse.getAccessToken());

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("비밀번호가 틀리면 회원을 탈퇴할 수 없다.")
    @Test
    void removeCustomerWithWrongPassword() {
        // given
        postRequest("/customers", new CustomerRegisterRequest(
                CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));
        final CustomerRemoveRequest customerRemoveRequest = new CustomerRemoveRequest(CUSTOMER_PASSWORD + "1");

        // when
        final ExtractableResponse<Response> response = postRequest("/auth/login",
                new TokenRequest(CUSTOMER_EMAIL, CUSTOMER_PASSWORD));
        final TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);

        final ExtractableResponse<Response> deleteResponse = deleteRequest(
                "/customers", customerRemoveRequest, tokenResponse.getAccessToken());

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
