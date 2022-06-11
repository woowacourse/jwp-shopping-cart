package woowacourse.shoppingcart.customer.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.customer.acceptance.CustomerRestHandler.assertThatException;
import static woowacourse.shoppingcart.customer.acceptance.CustomerRestHandler.회원가입;
import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;
import static woowacourse.support.acceptance.RestHandler.extractResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.auth.acceptance.AuthRestHandler;
import woowacourse.shoppingcart.auth.application.dto.response.TokenResponse;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRemoveRequest;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerUpdateResponse;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.support.acceptance.AcceptanceTest;

class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("인증이 필요없는 접근")
    @Nested
    class NonNecessaryAuthTest {

        @DisplayName("회원을 등록한다")
        @Test
        void registerCustomer() {
            final ExtractableResponse<Response> registerResponse = 회원가입();

            assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(extractResponse(registerResponse, CustomerResponse.class))
                    .extracting("email", "nickname")
                    .containsExactly(EMAIL_VALUE, NICKNAME_VALUE);
        }

        @DisplayName("잘못된 이메일 형식으로 회원을 등록한다")
        @ParameterizedTest
        @ValueSource(strings = {"", "@", "a@b", "a@b.", "a@b.c"})
        void registerCustomerWithInvalidEmail(final String email) {
            final ExtractableResponse<Response> registerResponse = 회원가입(email, NICKNAME_VALUE, PASSWORD_VALUE);

            assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThatException(registerResponse, CustomerExceptionCode.INVALID_FORMAT_EMAIL);
        }

        @DisplayName("잘못된 닉네임 형식으로 회원을 등록한다")
        @ParameterizedTest
        @ValueSource(strings = {
                "", " ", "  ", "   ",
                "a", "1234567890a"
        })
        void registerCustomerWithInvalidNickname(final String nickname) {
            final ExtractableResponse<Response> registerResponse = 회원가입(EMAIL_VALUE, nickname, PASSWORD_VALUE);

            assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThatException(registerResponse, CustomerExceptionCode.INVALID_FORMAT_NICKNAME);
        }

        @DisplayName("잘못된 비밀번호 형식으로 회원을 등록한다")
        @ParameterizedTest
        @ValueSource(strings = {
                "123456789", "12345678a", "12345678@",
                "1234567890", "1234567890aaa", "1234567890!@#"
        })
        void registerCustomerWithInvalidPassword(final String password) {
            final ExtractableResponse<Response> registerResponse = 회원가입(EMAIL_VALUE, NICKNAME_VALUE, password);

            assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThatException(registerResponse, CustomerExceptionCode.INVALID_FORMAT_PASSWORD);
        }

        @DisplayName("동일한 이메일로 회원을 등록한다")
        @Test
        void registerCustomerWithDuplicatedEmail() {
            회원가입();

            final ExtractableResponse<Response> registerResponse = 회원가입();

            assertThat(registerResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThatException(registerResponse, CustomerExceptionCode.ALREADY_EMAIL_EXIST);
        }
    }

    @DisplayName("인증이 필요한 접근")
    @Nested
    class NecessaryAuthTest {

        private String accessToken;

        @BeforeEach
        void setUp() {
            final TokenResponse tokenResponse = extractResponse(AuthRestHandler.회원가입_로그인(), TokenResponse.class);
            this.accessToken = tokenResponse.getAccessToken();
        }

        @DisplayName("회원의 개인정보를 조회한다.")
        @Test
        void findCustomer() {
            final ExtractableResponse<Response> profileResponse = CustomerRestHandler.개인정보조회(accessToken);

            assertThat(profileResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(extractResponse(profileResponse, CustomerResponse.class))
                    .extracting("email", "nickname")
                    .containsExactly(EMAIL_VALUE, NICKNAME_VALUE);
        }

        @DisplayName("회원의 닉네임을 변경한다.")
        @ParameterizedTest
        @ValueSource(strings = {"newNick"})
        void updateProfile(final String newNickname) {
            final ExtractableResponse<Response> profileUpdateResponse = CustomerRestHandler.개인정보변경(
                    new CustomerProfileUpdateRequest(newNickname), accessToken);

            assertThat(profileUpdateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(extractResponse(profileUpdateResponse, CustomerUpdateResponse.class).getNickname())
                    .isEqualTo(newNickname);
        }

        @DisplayName("잘못된 형식으로 회원의 닉네임을 변경한다.")
        @ParameterizedTest
        @ValueSource(strings = {"newNick"})
        void updateProfileWithInvalidFormat(final String newNickname) {
            final ExtractableResponse<Response> profileUpdateResponse = CustomerRestHandler.개인정보변경(
                    new CustomerProfileUpdateRequest(newNickname), accessToken);

            assertThat(profileUpdateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(extractResponse(profileUpdateResponse, CustomerUpdateResponse.class).getNickname())
                    .isEqualTo(newNickname);
        }

        @DisplayName("회원의 비밀번호를 변경한다.")
        @ParameterizedTest
        @ValueSource(strings = {"newqwer1234!@#$"})
        void updatePassword(final String newPassword) {
            final ExtractableResponse<Response> patchResponse = CustomerRestHandler.비밀번호변경(
                    new CustomerPasswordUpdateRequest(PASSWORD_VALUE, newPassword), accessToken);

            assertThat(patchResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("기존과 일치하지 않는 비밀번호로는 회원의 비밀번호를 변경할 수 없다.")
        @Test
        void updatePasswordWithInvalidFormat() {
            final ExtractableResponse<Response> patchResponse = CustomerRestHandler.비밀번호변경(
                    new CustomerPasswordUpdateRequest("wrong" + PASSWORD_VALUE, PASSWORD_VALUE), accessToken);

            assertThat(patchResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThatException(patchResponse, CustomerExceptionCode.MISMATCH_PASSWORD);
         }

        @DisplayName("회원을 탈퇴한다.")
        @Test
        void removeCustomer() {
            final ExtractableResponse<Response> deleteResponse = CustomerRestHandler.회원탈퇴(
                    new CustomerRemoveRequest(PASSWORD_VALUE), accessToken);

            assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("기존과 일치하지 않는 비밀번호로 회원을 탈퇴한다.")
        @Test
        void removeCustomerWithWrongPassword() {
            final ExtractableResponse<Response> deleteResponse = CustomerRestHandler.회원탈퇴(
                    new CustomerRemoveRequest("another" + PASSWORD_VALUE), accessToken);

            assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThatException(deleteResponse, CustomerExceptionCode.MISMATCH_PASSWORD);
        }
    }
}
