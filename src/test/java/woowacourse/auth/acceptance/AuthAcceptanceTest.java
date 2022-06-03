package woowacourse.auth.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.rowBasicPassword;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("등록된 회원이 로그인을 하면 토큰을 발급받는다.")
    @Test
    void loginReturnBearerToken() {
        // given
        회원가입을_한다("giron", rowBasicPassword);

        // when
        final ExtractableResponse<Response> extract = 로그인을_한다("giron", rowBasicPassword);
        final TokenResponse tokenResponse = extract.as(TokenResponse.class);

        // then
        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }

    @DisplayName("Bearer Auth 로그인 성공하고, 내 정보 조회를 요청하면 내 정보가 조회된다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        회원가입을_한다("giron", rowBasicPassword);

        final ExtractableResponse<Response> extract = 로그인을_한다("giron", rowBasicPassword);

        final TokenResponse tokenResponse = extract.as(TokenResponse.class);

        // when
        final ExtractableResponse<Response> responseMe = 내_정보를_조회한다(tokenResponse.getAccessToken());
        final CustomerResponse customerResponse = responseMe.as(CustomerResponse.class);
        // then
        // 내 정보가 조회된다
        assertThat(customerResponse.getUserName()).isEqualTo("giron");
    }

    @DisplayName("Bearer Auth 로그인 실패 - 유저 이름이 잘못된 경우 404-NOT_FOUND를 반환한다.")
    @Test
    void loginFailureWithWrongUserName() {
        // given
        회원가입을_한다("giron", rowBasicPassword);
        // when
        final ExtractableResponse<Response> extract = 로그인을_한다("tiki12", rowBasicPassword);

        final ErrorResponse errorResponse = extract.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(extract.header(HttpHeaders.AUTHORIZATION)).isNull(),
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("존재하지 않는 유저입니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 실패 - 비밀번호가 잘못된 경우 404-NOT_FOUND를 반환한다.")
    @Test
    void loginFailureWithWrongPassword() {
        // given
        회원가입을_한다("giron", rowBasicPassword);
        // when
        final ExtractableResponse<Response> extract = 로그인을_한다("giron", "wrongPassword");
        final ErrorResponse errorResponse = extract.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(extract.header(HttpHeaders.AUTHORIZATION)).isNull(),
                () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰으로 접근했을 때 - 401 UNAUTHORIZED를 반환한다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        회원가입을_한다("giron", rowBasicPassword);

        // when
        final ExtractableResponse<Response> responseMe = 내_정보를_조회한다("wrongAccessToken");
        final ErrorResponse errorResponse = responseMe.as(ErrorResponse.class);

        // then
        assertAll(
                () -> assertThat(responseMe.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("로그인할 때 유저 이름이 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void loginWithWrongUserName(String userName) {
        // when
        final ExtractableResponse<Response> response = 로그인을_한다(userName, "12345678");

        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("유저 이름은 빈칸일 수 없습니다.")
        );
    }

    @DisplayName("로그인할 때 바밀번호가 잘못된 경우 400-BAD_REQUEST를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void loginWithWrongPassword(String password) {

        // when
        final ExtractableResponse<Response> response = 로그인을_한다("giron", password);

        final ErrorResponse errorResponse = response.as(ErrorResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("비밀번호는 빈칸일 수 없습니다.")
        );
    }
}
