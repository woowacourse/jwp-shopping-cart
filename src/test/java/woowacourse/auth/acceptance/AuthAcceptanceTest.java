package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.AuthFixture.findById;
import static woowacourse.fixture.CustomerFixture.login;
import static woowacourse.fixture.CustomerFixture.signUp;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        signUp("test@woowacourse.com", "test", "123$ddddd");
        ExtractableResponse<Response> secondResponse = login("test@woowacourse.com", "123$ddddd");
        String token = secondResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> thirdResponse = findById(token);

        // then
        assertAll(
                () -> assertThat(thirdResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(thirdResponse.body().jsonPath().getString("userId")).isEqualTo(
                        "test@woowacourse.com"),
                () -> assertThat(thirdResponse.body().jsonPath().getString("nickname")).isEqualTo("test")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        ExtractableResponse<Response> thirdResponse = findById("invalidToken");

        // then
        assertAll(
                () -> assertThat(thirdResponse.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(thirdResponse.body().jsonPath().getString("message")).isEqualTo(
                        "로그인을 해주세요."
                )
        );
    }
}
