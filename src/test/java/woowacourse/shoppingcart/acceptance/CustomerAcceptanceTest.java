package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.nickname;
import static woowacourse.utils.Fixture.password;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.RestAssuredUtils.deleteWithToken;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;
import static woowacourse.utils.RestAssuredUtils.signOut;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.customer.CustomerProfileUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("회원관련 기능 인수테스트")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUpSuccess() {
        // given
        ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

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
        httpPost("/customers", signupRequest);

        // when
        ExtractableResponse<Response> response = signOut("/customers", "");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원 탈퇴를 진행한다.")
    @Test
    void signOutSuccess() {
        // given
        httpPost("/customers", signupRequest);

        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        String token = loginResponse.jsonPath().getString("accessToken");

        // when
        SignoutRequest signoutRequest = new SignoutRequest(password);
        ExtractableResponse<Response> response = deleteWithToken("/customers", token, signoutRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        String token = loginResponse.jsonPath().getString("accessToken");

        CustomerProfileUpdateRequest request = new CustomerProfileUpdateRequest("thor");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(request)
                .when().patch("/customers/profile")
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo("thor")
        );
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findCustomer() {
        // given
        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
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
}
