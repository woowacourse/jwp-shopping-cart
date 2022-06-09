package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("회원 관련 기능")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters", "InnerClassMayBeStatic"})
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입_정상_요청() {
        ExtractableResponse<Response> response = 회원이_저장되어_있음("loe0842", "dpepsWkd12!");

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.header("Location")).isEqualTo("/signin");
    }

    @Test
    void 중복된_아이디로_회원가입() {
        Map<String, Object> request = 회원_정보(
                "loe0842",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        회원_가입_요청(request);

        ExtractableResponse<Response> response = 회원_가입_요청(request);
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    void 아이디_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "1234567890123456",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("계정은 4 ~ 15자로 생성 가능합니다");
    }

    @Test
    void 아이디_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "123",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("계정은 4 ~ 15자로 생성 가능합니다");
    }

    @Test
    void 닉네임_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에덴에덴에덴에덴에덴에",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 닉네임_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 닉네임_빈값으로_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "     ",
                "dpepsWkd12!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        //when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 비밀번호_규정_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에덴",
                "dpeps123",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message"))
                .contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @Test
    void 비밀번호_글자수_초과해_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에덴",
                "aA345678901234567890!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

    @Test
    void 비밀번호_글자수_부족으로_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에덴",
                "aaAA11!",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

    @Test
    void 비밀번호_빈값으로_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "leo123",
                "에덴",
                "        ",
                "에덴 동산",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @Test
    void 주소_글자수_초과_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "loe0842",
                "에덴",
                "dpepsWkd12!",
                "에".repeat(256),
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("주소는 최대 255자까지 가능합니다.");
    }

    @Test
    void 주소_글자수_빈값_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "loe0842",
                "에덴",
                "dpepsWkd12!",
                "  ",
                "010",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("주소는 빈 값 생성이 불가능합니다.");
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
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }

    @Test
    void 핸드폰_번호_양식_실패() {
        // given
        Map<String, Object> request = 회원_정보(
                "loe0842",
                "에덴",
                "dpepsWkd12!",
                "에덴 동산",
                "공일공",
                "1234",
                "5678");

        // when
        ExtractableResponse<Response> response = 회원_가입_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("휴대폰 번호는 숫자만 가능합니다.");
    }

    @Test
    void 사용자_정보_조회() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        ExtractableResponse<Response> response = 회원_조회(accessToken);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(getValue(response, "account")).isEqualTo(account);
    }

    @Test
    void 존재하지_않는_사용자_정보_조회() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        회원_탈퇴(accessToken, password);

        ExtractableResponse<Response> response = 회원_조회(accessToken);

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(getValue(response, "message")).contains("존재하지 않는 사용자입니다.");
    }

    @Test
    void 잘못된_토큰으로_회원_관련_요청() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken + "hi")
                .when()
                .get("/customers")
                .then()
                .log().all()
                .extract();

        String errorMessage = getValue(response, "message");

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(errorMessage).isEqualTo("유효하지 않은 토큰입니다.");
    }

    @Test
    void 회원_탈퇴() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        ExtractableResponse<Response> response = 회원_탈퇴(accessToken, password);

        assertThat(response.statusCode()).isEqualTo(204);
    }

    @Test
    void 비밀번호_불일치로_회원_탈퇴_실패() {
        String account = "leo8842";
        String password = "dpepsWkd12!";

        String accessToken = 회원_가입_후_토큰_발급(account, password);

        ExtractableResponse<Response> response = 회원_탈퇴(accessToken, "1111");

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(getValue(response, "message")).contains("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원정보_수정() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        ExtractableResponse<Response> customerResponse = 회원_조회(accessToken);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(getValue(customerResponse, "nickname")).isEqualTo(nickname);
        assertThat(getValue(customerResponse, "address")).isEqualTo(address);
        assertThat(getPhoneNumberValue(customerResponse, "start")).isEqualTo(start);
        assertThat(getPhoneNumberValue(customerResponse, "middle")).isEqualTo(middle);
        assertThat(getPhoneNumberValue(customerResponse, "last")).isEqualTo(last);
    }

    @Test
    void 닉네임_글자수_초과해_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임변경닉네임호";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 닉네임_글자수_부족으로_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "1";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 닉네임_빈값_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "     ";
        String address = "변경주소";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("닉네임은 2 ~ 10자로 생성 가능합니다");
    }

    @Test
    void 주소_글자수_초과_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임";
        String address = "연".repeat(256);
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("주소는 최대 255자까지 가능합니다.");
    }

    @Test
    void 주소_빈값_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임";
        String address = "    ";
        String start = "010";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("주소는 빈 값 생성이 불가능합니다.");
    }

    @Test
    void 휴대폰번호_형식_불만족_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임";
        String address = "에덴네";
        String start = "0100";
        String middle = "8888";
        String last = "9999";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }

    @Test
    void 휴대폰번호_빈값_수정_실패() {
        // given
        String account = "leo8842";
        String password = "dpepsWkd12!";
        String nickname = "변경닉네임";
        String address = "에덴네";
        String start = "  ";
        String middle = "  ";
        String last = " ";

        String accessToken = 회원_가입_후_토큰_발급(account, password);
        Map<String, Object> request = 회원_수정_정보(nickname, address, start, middle, last);

        // when
        ExtractableResponse<Response> response = 회원_수정(accessToken, request);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(getValue(response, "message")).contains("휴대폰 번호양식이 불일치 합니다.");
    }
}
