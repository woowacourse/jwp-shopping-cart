package woowacourse.auth.acceptance;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import Fixture.SimpleRestAssured;
import io.restassured.RestAssured;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.response.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);

        String accessToken = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(YAHO_USERNAME, YAHO_RAW_PASSWORD.getValue()))
                .when().post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();

        // when
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);

        // then
        assertThat(customerResponse.getUsername()).isEqualTo(YAHO_USERNAME);
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        SimpleRestAssured.saveCustomer(MAT_SAVE_REQUEST);

        // when & then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(YAHO_USERNAME, YAHO_ENCODED_PASSWORD.getValue()))
                .when().post("/api/auth/token")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        String accessToken = "안녕나는.유효하지.않은토큰";

        // then
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }
}
