package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.CUSTOMER_URI;
import static woowacourse.ShoppingCartFixture.LOGIN_URI;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;

@DisplayName("인증 관련 기능")
@Sql("/truncate.sql")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        final TokenRequest 로그인요청 = new TokenRequest(잉_회원생성요청.getEmail(), 잉_회원생성요청.getPassword());
        final TokenResponse 엑세스토큰 = post(LOGIN_URI, 로그인요청).as(TokenResponse.class);

        // when
        final ExtractableResponse<Response> 회원조회응답 = getWithToken(CUSTOMER_URI, 엑세스토큰.getAccessToken());
        final CustomerResponse 회원조회결과 = 회원조회응답.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(회원조회결과.getEmail()).isEqualTo(회원생성요청.getEmail()),
                () -> assertThat(회원조회결과.getName()).isEqualTo(회원생성요청.getName())
        );
    }

    @DisplayName("로그인시, 잘못된 비밀번호가 들어온 경우 로그인에 실패한다.")
    @Test
    void myInfoWithIncorrectPasswordShouldFail() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        // when
        final TokenRequest 로그인요청 = new TokenRequest(잉_회원생성요청.getEmail(), 잉_회원생성요청.getPassword() + "wrong");
        final ExtractableResponse<Response> 로그인실패응답 = post(LOGIN_URI, 로그인요청);

        // then
        assertAll(
                () -> assertThat(로그인실패응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThatCode(() -> 로그인실패응답.as(ExceptionResponse.class))
                        .doesNotThrowAnyException()
        );
    }

    @DisplayName("로그인시, 잘못된 이메일이 들어온 경우 로그인에 실패한다.")
    @Test
    void myInfoWithIncorrectEmailShouldFail() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        // when
        final TokenRequest 로그인요청 = new TokenRequest(잉_회원생성요청.getEmail() + "wrong", 잉_회원생성요청.getPassword());
        final ExtractableResponse<Response> 로그인실패응답 = post(LOGIN_URI, 로그인요청);

        // then
        assertAll(
                () -> assertThat(로그인실패응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(로그인실패응답.as(ExceptionResponse.class).getMessage())
                        .isEqualTo("로그인에 실패했습니다")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        // when
        final ExtractableResponse<Response> 회원조회응답 = getWithToken(CUSTOMER_URI, "NotValidToken");

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThatCode(() -> 회원조회응답.as(ExceptionResponse.class))
                        .doesNotThrowAnyException()
        );
    }

    @DisplayName("Bearer Auth 빈 값으로 오는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "Bearer", "Bearer "})
    void myInfoWithBlankBearerAuth(String token) {
        // when
        final ExtractableResponse<Response> 회원조회응답 = RestAssured.given().log().all()
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(CUSTOMER_URI)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(회원조회응답.as(ExceptionResponse.class).getMessage())
                        .isEqualTo("인증 정보가 확인되지 않습니다")
        );
    }
}
