package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일과 패스워드로 로그인한다.")
    @Test
    void login() {
        // given
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                email, nickname, password);
        createCustomer(customerCreateRequest);


        // when
        TokenRequest tokenRequest = new TokenRequest(email, password);
        ExtractableResponse<Response> loginResponse = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/login")
                .then().extract();

        // then
        assertThat(loginResponse.body().jsonPath().getString("accessToken")).isNotNull();
    }


    @DisplayName("로그인 후 정보를 조회한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                email, nickname, password);
        createCustomer(customerCreateRequest);

        TokenRequest tokenRequest = new TokenRequest(email, password);

        ExtractableResponse<Response> loginResponse = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/login")
                .then().extract();


        // when
        String accessToken = loginResponse.body().jsonPath().getString("accessToken");

        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().log().all()
                .get("/api/customers/me")
                .then().log().all()
                .extract().as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getNickname()).isEqualTo(nickname)
        );
    }

    @DisplayName("로그인 하지 않고, 정보 조회 시 예외가 발생한다.")
    @Test
    void findCustomerInfoNotLogin() {
        // given && when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/api/customers/me")
            .then().log().all()
            .extract();

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("인증되지 않은 사용자입니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(
                email, nickname, password);
        createCustomer(customerCreateRequest);

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        TokenRequest tokenRequest = new TokenRequest(email, password+"1");


        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/login")
                .then().extract();

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }
}
