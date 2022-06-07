package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일과 패스워드로 로그인하여 엑세스 토큰을 발급받는다.")
    @Test
    void login() {
        // given: 회원이 생성되어 있다.
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        createCustomer(email, nickname, password);

        // when: 생성된 회원의 이메일과 비밀번호로 로그인 요청을 보내면
        String accessToken = 로그인_요청(new TokenRequest(email, password));

        // then: 엑세스 토큰이 발급된다.
        assertThat(accessToken).isNotNull();
    }


    @DisplayName("로그인 후 회원정보를 조회한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given: 생성된 회원정보로 로그인한다.
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        createCustomer(email, nickname, password);

        String accessToken  = 로그인_요청(new TokenRequest(email, password));

        // when: 회원정보를 조회하면
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().log().all()
                .get("/api/customers/me")
                .then().log().all()
                .extract().as(CustomerResponse.class);

        // then: 회원정보를 응답한다.
        assertAll(
                () -> assertThat(customerResponse.getEmail()).isEqualTo(email),
                () -> assertThat(customerResponse.getNickname()).isEqualTo(nickname)
        );
    }

    @DisplayName("로그인 하지 않고 정보 조회 시 예외가 발생한다.")
    @Test
    void findCustomerInfoNotLogin() {
        // given && when: 로그인하지 않고 회원정보 조회를 시도하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when().log().all()
            .get("/api/customers/me")
            .then().log().all()
            .extract();

        // then: 미인증 예외를 응답받는다.
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
            () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("인증되지 않은 사용자입니다.")
        );
    }

    @DisplayName("잘못된 password로 로그인 시도시 예외가 발생한다.")
    @Test
    void myInfoWithBadBearerAuth() {
        // given: 회원이 등록되어 있다.
        String email = "beomWhale1@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        createCustomer(email, nickname, password);

        // when: 잘못된 password로 로그인 요청을 보내면
        TokenRequest tokenRequest = new TokenRequest(email, password+"1");
        ExtractableResponse<Response> response = sendLoginRequest(tokenRequest);

        // then: 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }

    private ExtractableResponse<Response> sendLoginRequest(TokenRequest tokenRequest) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/login")
                .then().extract();
    }

    private void createCustomer(String email, String nickname, String password) {
        sendCreateCustomerRequest(new CreateCustomerRequest(email, nickname, password));
    }

    private ExtractableResponse<Response> sendCreateCustomerRequest(CreateCustomerRequest customerCreateRequest) {
        return createCustomer(customerCreateRequest);
    }
}
