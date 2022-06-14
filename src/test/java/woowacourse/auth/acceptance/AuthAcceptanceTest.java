package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.AuthFixture.findById;
import static woowacourse.fixture.CustomerFixture.login;
import static woowacourse.fixture.CustomerFixture.signUp;

import io.restassured.RestAssured;
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

        // when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("userId", is("test@woowacourse.com"))
                .body("nickname", is("test"));
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        String token = "invalidtoken";

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .body("message", is("권한이 없습니다."));
    }
}
