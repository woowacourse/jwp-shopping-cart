package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        회원_가입(회원_정보(account,
                password
        ));

        // when
        ExtractableResponse<Response> response = 로그인_후_토큰_발급(account, password);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(findValue(response, "accessToken")).isNotNull();
    }

    @Test
    void 로그인_비밀번호_불일치로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        회원_가입(회원_정보(account,
                password
        ));

        // when
        ExtractableResponse<Response> response = 로그인_후_토큰_발급(account, "dpepsWkd");

        // then
        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(findValue(response, "message"))
                .contains("로그인이 불가능합니다.");
    }


    private void 회원_가입(Map<String, Object> request) {
        RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signup")
                .then()
                .log().all()
                .extract();
    }

    private Map<String, Object> 회원_정보(String account, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("nickname", "에덴");
        request.put("password", password);
        request.put("address", "에덴 동산");

        Map<String, String> phoneNumber = new HashMap<>();
        phoneNumber.put("start", "010");
        phoneNumber.put("middle", "1234");
        phoneNumber.put("last", "5678");
        request.put("phoneNumber", phoneNumber);
        return request;
    }

    private ExtractableResponse<Response> 로그인_후_토큰_발급(String account, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("password", password);

        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signin")
                .then()
                .log().all()
                .extract();
    }

    private String findValue(ExtractableResponse<Response> response, String value) {
        return response.body().jsonPath().getString(value);
    }
}
