package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.utils.Fixture.email;
import static woowacourse.auth.utils.Fixture.nickname;
import static woowacourse.auth.utils.Fixture.password;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("회원관련 기능 인수테스트")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUpSuccess() {
        // given
        ExtractableResponse<Response> response = signUp();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
        );
    }

    @DisplayName("토큰이 없을 때 회원 탈퇴를 할 수 없다.")
    @Test
    void signOutNotLogin() {
        // given
        signUp();

        // when
        ExtractableResponse<Response> response = signOut("");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원 탈퇴를 진행한다.")
    @Test
    void signOutSuccess() {
        // given
        signUp();
        ExtractableResponse<Response> loginResponse = login();
        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> response = signOut(token);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        signUp();
        ExtractableResponse<Response> loginResponse = login();
        String token = loginResponse.jsonPath().getString("accessToken");

        CustomerUpdateRequest request = new CustomerUpdateRequest("thor", password, "b1234!");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(request)
                .when().patch("/customers")
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo("thor")
        );
    }

    @DisplayName("회원 정보을 조회한다.")
    @Test
    void findCustomer() {
        // given
        signUp();
        ExtractableResponse<Response> loginResponse = login();
        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(token)
                .when().get("/customers")
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email)
        );
    }

    private ExtractableResponse<Response> signUp() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignupRequest(email, password, nickname))
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> login() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new TokenRequest(email, password))
                .when().post("/auth/login")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> signOut(String token) {
        return RestAssured.given().log().all()
                .auth().oauth2(token)
                .when().delete("/customers")
                .then().log().all()
                .extract();
    }
}
