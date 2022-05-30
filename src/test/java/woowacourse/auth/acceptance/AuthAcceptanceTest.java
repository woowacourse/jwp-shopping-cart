package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 성공")
    @Test
    void login() {
        // given
        String email = "email@email.com";
        String password = "1q2w3e4r";

        CustomerCreationRequest signUpRequest = new CustomerCreationRequest(email, password, "kun");
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all();

        TokenRequest request = new TokenRequest(email, password);

        // when
        ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/login")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue(String.class));
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
