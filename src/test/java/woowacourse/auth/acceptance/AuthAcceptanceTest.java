package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        signUpCustomer();
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고
        String accessToken = RestAssured.given().log().all()
                .body(new TokenRequest("forky", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
//        CustomerResponse actual = RestAssured.given().log().all()
//                .auth().oauth2(accessToken)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/customers/me")
//                .then().log().all()
//                .extract()
//                .as(CustomerResponse.class);
//        // then
//        // 내 정보가 조회된다
//        assertAll(
//                () -> assertThat(actual.getUserName()).isEqualTo("forky"),
//                () -> assertThat(actual.getPassword()).isEqualTo("forky@1234")
//        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        IllegalArgumentException exception = RestAssured.given().log().all()
                .body(new TokenRequest("forky", "kth@990303"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                // 토큰 발급 요청이 거부된다
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(IllegalArgumentException.class);
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }

    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signup")
                .then().log().all();
    }
}
