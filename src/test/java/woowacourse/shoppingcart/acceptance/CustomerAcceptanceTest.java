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

    @Test
    void 아이디_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보("1234567890123456",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("계정은 4 ~ 15자로 생성 가능합니다")).isTrue();
    }

    @Test
    void 아이디_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보("123",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("계정은 4 ~ 15자로 생성 가능합니다")).isTrue();
    }

    @Test
    void 닉네임_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴에덴에덴에덴에덴에",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("닉네임은 2 ~ 10자로 생성 가능합니다")).isTrue();
    }

    @Test
    void 닉네임_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("닉네임은 2 ~ 10자로 생성 가능합니다")).isTrue();
    }

    @Test
    void 닉네임_빈값으로_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "     ",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("닉네임은 2 ~ 10자로 생성 가능합니다")).isTrue();
    }

    @Test
    void 비밀번호_규정_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "dpeps123",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.")).isTrue();
    }

    @Test
    void 비밀번호_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "aA345678901234567890!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("비밀번호는 8 ~ 20자로 생성 가능합니다.")).isTrue();
    }

    @Test
    void 비밀번호_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "aaAA11!",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("비밀번호는 8 ~ 20자로 생성 가능합니다.")).isTrue();
    }

    @Test
    void 비밀번호_빈값으로_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "        ",
                "에덴 동산",
                "010",
                "1234",
                "5678");
        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.")).isTrue();
    }

    @Test
    void 주소_글자수_초과_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "dpepsWkd12!",
                "에".repeat(256),
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("주소는 최대 255자까지 가능합니다.")).isTrue();
    }

    @Test
    void 주소_글자수_빈값_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "dpepsWkd12!",
                "  ",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("주소는 빈 값 생성이 불가능합니다.")).isTrue();
    }

    @Test
    void 핸드폰_번호_길이_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "0101",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("휴대폰 번호양식이 불일치 합니다.")).isTrue();
    }

    @Test
    void 핸드폰_번호_양식_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "공일공",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        int status = response.statusCode();
        String errorMessage = response.body().jsonPath().getString("message");

        assertThat(status).isEqualTo(400);
        assertThat(errorMessage.contains("휴대폰 번호는 숫자만 가능합니다.")).isTrue();
    }

    @Test
    void 로그인() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        회원_가입(
                회원_정보(account,
                        "에덴",
                        password,
                        "에덴 동산",
                        "010",
                        "1234",
                        "5678"));

        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("password", password);

        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signin")
                .then()
                .log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body().jsonPath().getString("accessToken")).isNotNull();
    }

    @Test
    void 비밀번호_불일치_로그인_실패() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        회원_가입(
                회원_정보(account,
                        "에덴",
                        password,
                        "에덴 동산",
                        "010",
                        "1234",
                        "5678"));

        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("password", "dpepsWkd");

        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signin")
                .then()
                .log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.body().jsonPath().getString("message"))
                .contains("로그인이 불가능합니다.");
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
