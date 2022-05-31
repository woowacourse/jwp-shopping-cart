package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {
    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        Customer customer = new Customer("email", "Pw123456!", "name", "010-1234-5678", "address");
        RestAssured.given().log().all()
                .body(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured.given().log().all()
                .body(new TokenRequest("email", "Pw123456!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
        // when
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerResponse.class);

        //TODO : RestAssured status code 검증 위치 통일

        // then
        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-1234-5678", "address");
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
