package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @Test
    void 회원_가입() {
        ExtractableResponse<Response> createResponse = 회원_가입("testx", "1A2B5c78!");

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 중복된_이름으로_회원_가입() {
        회원_가입("testx", "1A2B5c78!");
        ExtractableResponse<Response> createResponse = 회원_가입("testx", "1A2B5c78!");

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원가입_시_누락된_필드값_존재() {
        ExtractableResponse<Response> createResponse = 회원_가입("testx", null);

        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 내_정보_조회() {
        // given
        회원_가입("testx", "1A2B5c78!");
        String accessToken = 로그인_후_토큰_획득("testx", "1A2B5c78!");

        // when
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.body().jsonPath().getString("userName")).isEqualTo("testx");
    }

    @Test
    void 토큰을_발급받지_않고_내_정보_조회() {
        // when
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 중복_아이디_검사() {
        회원_가입("testx", "1a2b3c4D!");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/api/customers/exists?userName=testx")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getBoolean("isDuplicate")).isTrue();
    }

    @Test
    void 중복되지_않은_아이디_검사() {
        회원_가입("testx", "1a2b3c4D!");
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/api/customers/exists?userName=testxy")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.body().jsonPath().getBoolean("isDuplicate")).isFalse();
    }

    @Test
    void 내_정보_수정() {
        // given
        회원_가입("testx", "1A2B5c78!");
        String accessToken = 로그인_후_토큰_획득("testx", "1A2B5c78!");

        // when
        ExtractableResponse<Response> editResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("testx", "1A2C5c78!"))
                .when().put("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        ExtractableResponse<Response> loginResponse = RestAssured
                .given().log().all()
                .body(new TokenRequest("testx", "1A2C5c78!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();

        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 토큰을_발급받지_않고_내_정보_수정() {
        // when
        ExtractableResponse<Response> editResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest("testx", "1A2B5c78!"))
                .when().put("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 회원_탈퇴() {
        // given
        회원_가입("testx", "1A2B5c78!");
        String accessToken = 로그인_후_토큰_획득("testx", "1A2B5c78!");

        // when
        ExtractableResponse<Response> deleteResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        ExtractableResponse<Response> getResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 토큰을_발급받지_않고_탈퇴() {
        // when
        ExtractableResponse<Response> deleteResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private String 로그인_후_토큰_획득(String name, String password) {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(name, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract().as(TokenResponse.class).getAccessToken();
    }

    private ExtractableResponse<Response> 회원_가입(String name, String password) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CustomerRequest(name, password))
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }
}
