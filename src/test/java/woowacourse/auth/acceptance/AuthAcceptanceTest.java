package woowacourse.auth.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.acceptance.ResponseCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ResponseCreator.getCustomers;
import static woowacourse.shoppingcart.acceptance.ResponseCreator.postLogin;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        ResponseCreator.postCustomers("wishoon@gmail.com", "qwer1234@Q", "rookie");

        // when
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("wishoon@gmail.com", "qwer1234@Q");

        // then
        assertThat(로그인_응답됨.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        ResponseCreator.postCustomers("wishoon@gmail.com", "qwer1234@Q", "rookie");

        // when
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("wishoon1@gmail.com", "qwer1234@Q");

        // then
        assertThat(로그인_응답됨.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        ResponseCreator.postCustomers("wishoon@gmail.com", "qwer1234@Q", "rookie");
        TokenResponse 위변조_토큰 = new TokenResponse("Forgery_Token");

        // when
        ExtractableResponse<Response> 회원_조회_응답됨 = getCustomers(위변조_토큰);

        // then
        assertThat(회원_조회_응답됨.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
