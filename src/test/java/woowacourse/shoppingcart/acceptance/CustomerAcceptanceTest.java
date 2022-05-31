package woowacourse.shoppingcart.acceptance;

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

@DisplayName("회원 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters", "InnerClassMayBeStatic"})
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입_정상_요청() {
        ExtractableResponse<Response> response = 회원_가입(
                회원_정보("loe0842",
                        "에덴",
                        "dpepsWkd12!",
                        "에덴 동산",
                        "010",
                        "1234",
                        "5678"));

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.header("Location")).isEqualTo("/signin");
    }

    @Test
    void 중복된_아이디로_회원가입() {
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        회원_가입(request);

        ExtractableResponse<Response> response = 회원_가입(request);
        assertThat(response.statusCode()).isEqualTo(400);
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

    private Map<String, Object> 회원_정보(String account, String nickname, String password,
            String address, String start, String middle, String last) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("nickname", nickname);
        request.put("password", password);
        request.put("address", address);
        request.put("phoneNumber", 휴대폰_정보(start, middle, last));
        return request;
    }

    private Map<String, String> 휴대폰_정보(String start, String middle, String last) {
        Map<String, String> phoneNumber = new HashMap<>();
        phoneNumber.put("start", start);
        phoneNumber.put("middle", middle);
        phoneNumber.put("last", last);
        return phoneNumber;
    }

    private ExtractableResponse<Response> 회원_가입(Map<String, Object> request) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signup")
                .then()
                .log().all()
                .extract();
    }
}
