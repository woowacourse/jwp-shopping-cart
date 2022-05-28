package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 사용자_생성_요청("loginId", "seungpapang", "1a2s3d4f");

        사용자_추가됨(response);
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }

    public static ExtractableResponse<Response> 사용자_생성_요청(String loginId, String username, String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("loginId", loginId);
        requestBody.put("username", username);
        requestBody.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }

    public static void 사용자_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
