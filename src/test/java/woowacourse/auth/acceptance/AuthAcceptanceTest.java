package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

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
        CustomerRequest customerRequest = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/api/customers")
                .then().log().all();

        // when
        TokenRequest request = new TokenRequest("username", "wrongPassword12@");
        ExtractableResponse<Response> extracted = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/auth/token")
                .then().log().all().extract();

        // then
        assertThat(extracted.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/api/customers")
                .then().log().all();

        // when
        String invalidToken = "Bearer aaaaaa.bbbbbb.ccccc";
        ExtractableResponse<Response> authResponse = RestAssured.given().log().all()
                .header(new Header("Authorization", invalidToken))
                .when().get("/api/customers/me")
                .then().log().all().extract();

        // then
        assertThat(authResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
