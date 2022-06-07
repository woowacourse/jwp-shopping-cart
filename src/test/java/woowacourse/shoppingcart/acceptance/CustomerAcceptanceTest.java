package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입_정상_요청() {
        // given

        // when
        ExtractableResponse<Response> response = 회원_가입(
                회원_정보("loe0842",
                        "에덴",
                        "leoLeo123!",
                        "에덴 동산",
                        "010",
                        "1234",
                        "5678"));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header(LOCATION)).isEqualTo("/signin");
    }

    @Test
    void 회원_가입_중복된_계정으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        회원_가입(request);

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원_가입_아이디_글자수_초과로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("1234567890123456",
                "에덴",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("계정은 4 ~ 15자로 생성 가능합니다");
    }

    @Test
    void 회원_가입_아이디_글자수_부족으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("123",
                "에덴",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("계정은 4 ~ 15자로 생성 가능합니다");
    }

    @Test
    void 회원_가입_닉네임_글자수_초과로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴에덴에덴에덴에덴에",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_가입_닉네임_글자수_부족으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_가입_닉네임_빈값으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "     ",
                "leoLeo123!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_가입_잘못된_비밀번호_양식으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "dpeps123",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @Test
    void 회원_가입_비밀번호_글자수_초과로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "aA345678901234567890!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

    @Test
    void 회원_가입_비밀번호_글자수_부족으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "aaAA11!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

    @Test
    void 회원_가입_비밀번호_빈값으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("leo123",
                "에덴",
                "        ",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @Test
    void 회원_가입_주소_글자수_초과로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "leoLeo123!",
                "에".repeat(256),
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("주소는 최대 255자까지 가능합니다.");
    }

    @Test
    void 회원_가입_주소_빈값으로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "leoLeo123!",
                "  ",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("주소는 빈 값 생성이 불가능합니다.");
    }

    @Test
    void 회원_가입_휴대폰번호_길이_불일치로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "leoLeo123!",
                "에덴 동산",
                "0101",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }

    @Test
    void 회원_가입_휴대폰번호_양식_불일치로_인해_실패() {
        // given
        Map<String, Object> request = 회원_정보("loe0842",
                "에덴",
                "leoLeo123!",
                "에덴 동산",
                "공일공",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("휴대폰 번호는 숫자만 가능합니다.");
    }

    @Test
    void 사용자_정보_조회() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        // when
        ExtractableResponse<Response> response = 회원_조회(accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(findValue(response, "account")).isEqualTo(account);
    }

    @Test
    void 존재하지_않는_사용자_정보_조회() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        회원_탈퇴(accessToken, password);

        // when
        ExtractableResponse<Response> response = 회원_조회(accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(findValue(response, "message")).contains("존재하지 않는 사용자입니다.");
    }

    @Test
    void 잘못된_토큰으로_회원_관련_요청() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        // when
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER + accessToken + "hi")
                .when()
                .get("/customers")
                .then()
                .log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(findValue(response, "message")).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    void 회원_탈퇴() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        // when
        ExtractableResponse<Response> response = 회원_탈퇴(accessToken, password);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 회원_탈퇴_비밀번호_불일치로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String accessToken = 회원_가입_후_토큰_발급(account, password);

        // when
        ExtractableResponse<Response> response = 회원_탈퇴(accessToken, "1111");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(findValue(response, "message")).contains("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원_정보_수정() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        ExtractableResponse<Response> customerResponse = 회원_조회(accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(findValue(customerResponse, "nickname")).isEqualTo(nickname);
        assertThat(findValue(customerResponse, "address")).isEqualTo(address);
        assertThat(findPhoneNumberValue(customerResponse, "start")).isEqualTo(start);
        assertThat(findPhoneNumberValue(customerResponse, "middle")).isEqualTo(middle);
        assertThat(findPhoneNumberValue(customerResponse, "last")).isEqualTo(last);
    }

    @Test
    void 회원_정보_수정_닉네임_글자수_초과로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임변경닉네임호";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_정보_수정_닉네임_글자수_부족으로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "1";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_정보_수정_닉네임_빈값으로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "     ";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 회원_정보_수정_주소_글자수_초과로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임";
        String address = "연".repeat(256);
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("주소는 최대 255자까지 가능합니다.");
    }

    @Test
    void 회원_정보_수정_주소_빈값으로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임";
        String address = "    ";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("주소는 빈 값 생성이 불가능합니다.");
    }

    @Test
    void 회원_정보_수정_휴대폰번호_양식_불일치로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임";
        String address = "에덴네";
        String start = "0100";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }

    @Test
    void 회원_정보_수정_휴대폰번호_빈값으로_인해_실패() {
        // given
        String account = "leo8842";
        String password = "leoLeo123!";
        String nickname = "변경닉네임";
        String address = "에덴네";
        String start = "  ";
        String middle = "  ";
        String last = " ";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 수정할_회원_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(findValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }

    private String findPhoneNumberValue(ExtractableResponse<Response> response, String value) {
        return String.valueOf(response.body().jsonPath().getMap("phoneNumber").get(value));
    }

    private ExtractableResponse<Response> 회원_조회(String accessToken) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, BEARER + accessToken)
                .when().get("/customers")
                .then().log().all()
                .extract();
    }

    private Map<String, Object> 수정할_회원_정보(String nickname, String address, String start, String middle, String last) {
        Map<String, Object> request = new HashMap<>();
        request.put("nickname", nickname);
        request.put("address", address);
        request.put("phoneNumber", 휴대폰_정보(start, middle, last));
        return request;
    }

    private ExtractableResponse<Response> 회원_수정(String accessToken, Map<String, Object> request) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, BEARER + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/customers")
                .then().log().all()
                .extract();
    }
}
