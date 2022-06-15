package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.CUSTOMER_URI;
import static woowacourse.ShoppingCartFixture.LOGIN_URI;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.auth.ui.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.response.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.response.ExceptionResponse;

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
        final ExtractableResponse<Response> 회원조회응답 = get(CUSTOMER_URI, 엑세스토큰.getAccessToken());
        final CustomerResponse 회원조회결과 = 회원조회응답.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(회원조회결과.getEmail()).isEqualTo(회원생성요청.getEmail()),
                () -> assertThat(회원조회결과.getName()).isEqualTo(회원생성요청.getName())
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
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

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        // when
        final ExtractableResponse<Response> 회원조회응답 = get(CUSTOMER_URI, "NotValidToken");

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThatCode(() -> 회원조회응답.as(ExceptionResponse.class))
                        .doesNotThrowAnyException()
        );
    }
}
