package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> createResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("test", "1234"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isEqualTo("/api/customers/test");
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        ExtractableResponse<Response> createResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("test", "1234"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("test", "1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // when
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(createResponse.header("Location"))
                .then().log().all()
                .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.body().jsonPath().getString("name")).isEqualTo("test");
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        ExtractableResponse<Response> createResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("test", "1234"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("test", "1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // when
        ExtractableResponse<Response> editResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("updated", "1255"))
                .when().put(createResponse.header("Location"))
                .then().log().all()
                .extract();

        // then
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/updated")
                .then().log().all()
                .extract();

        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.body().jsonPath().getString("name")).isEqualTo("updated");
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        ExtractableResponse<Response> createResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("test", "1234"))
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest("test", "1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login/token")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();

        // when
        ExtractableResponse<Response> deleteResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(createResponse.header("Location"))
                .then().log().all()
                .extract();

        // then
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/test")
                .then().log().all()
                .extract();

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
