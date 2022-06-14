package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원가입;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원조회;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원탈퇴;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 시 Bearer 토큰이 생성된다.")
    @Test
    void createToken() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        // when
        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // then
        assertThat(token).isNotBlank();
    }

    @DisplayName("Bearer Auth 로그인에 성공한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // when
        ExtractableResponse<Response> response = 회원조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("Bearer Auth 로그인에 실패한다.")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        // when
        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "012345");
        ExtractableResponse<Response> response = 로그인(signInRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰으로 회원 조회를 할 수 없다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        로그인(signInRequest);

        // when
        String token = "dummy";
        ExtractableResponse<Response> response = 회원조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("회원 탈퇴 후 기존의 토큰을 보내 회원 조회를 할 수 없다.")
    void myInfoWithNotExistBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456");
        회원탈퇴(deleteCustomerRequest, token);

        // when
        ExtractableResponse<Response> response = 회원조회(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("새로운 토큰을 발급할 수 있다.")
    void reIssueToken() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        회원가입(signUpRequest);

        SignInRequest signInRequest = new SignInRequest("rennon@woowa.com", "123456");
        String token = 로그인(signInRequest)
                .as(SignInResponse.class)
                .getToken();

        // when
        String newToken = 토큰_재발급(token)
                .as(SignInResponse.class)
                .getToken();

        // then
        assertThat(newToken).isNotNull();
    }

    private ExtractableResponse<Response> 토큰_재발급(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when().post("/token/refresh")
                .then().log().all()
                .extract();
    }
}
