package woowacourse.shoppingcart.acceptance.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde123!");
        RestAssured
                .given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signUp")
                .then();
        // id, password 를 사용해 로그인하여 토큰을 발급받고
        String accessToken = RestAssured
                .given().log().all()
                .body(new CustomerLoginRequest("jo@naver.com", "abcde123!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/login")
                .then().log().all()
                .extract().as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerResponse customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(customerResponse.getUserId()).isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname()).isEqualTo(customerRequest.getNickname())
        );
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
