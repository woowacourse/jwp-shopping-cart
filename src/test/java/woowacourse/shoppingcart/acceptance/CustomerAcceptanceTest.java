package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.내_정보_조회_요청;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.로그인_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.LoginRequest;

@SuppressWarnings("NonAsciiChracters")
@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");

        사용자_추가됨(response);
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId@gmail.com", "12345678aA!");
        ExtractableResponse<Response> response = 로그인_요청(loginRequest);
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        ExtractableResponse<Response> getMeResponse = 내_정보_조회_요청(tokenResponse);
        CustomerResponse customerResponse = getMeResponse.as(CustomerResponse.class);

        assertAll(() -> {
            assertThat(getMeResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(customerResponse).extracting("loginId", "name")
                .containsExactly("loginId@gmail.com", "seungpapang");
        });
    }


    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId@gmail.com", "12345678aA!");
        ExtractableResponse<Response> response = 로그인_요청(loginRequest);
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        ExtractableResponse<Response> updatedResponse = 내정보_수정_요청(tokenResponse.getAccessToken(),
            "loginId@gmail.com", "angie", "12345678aA!");

        assertAll(() -> {
            assertThat(updatedResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(updatedResponse.as(CustomerResponse.class)).extracting("loginId", "name")
                .containsExactly("loginId@gmail.com", "angie");
        });
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        사용자_생성_요청("loginId@gmail.com", "seungpapang", "12345678aA!");
        LoginRequest loginRequest = new LoginRequest("loginId@gmail.com", "12345678aA!");
        ExtractableResponse<Response> response = 로그인_요청(loginRequest);
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        ExtractableResponse<Response> deletedResponse = 회원_탈퇴_요청(tokenResponse.getAccessToken(), "12345678aA!");

        assertThat(deletedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> 회원_탈퇴_요청(String accessToken, String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", password);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(accessToken)
            .body(requestBody)
            .when().delete("/customers/me")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 사용자_생성_요청(String loginId, String username, String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("loginId", loginId);
        requestBody.put("name", username);
        requestBody.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내정보_수정_요청(String accessToken, String loginId, String username, String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("loginId", loginId);
        requestBody.put("name", username);
        requestBody.put("password", password);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(accessToken)
            .body(requestBody)
            .when().put("/customers/me")
            .then().log().all()
            .extract();
    }


    public static void 사용자_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

}
