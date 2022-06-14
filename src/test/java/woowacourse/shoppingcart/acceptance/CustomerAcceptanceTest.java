package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.is;
import static woowacourse.fixture.AuthFixture.findById;
import static woowacourse.fixture.AuthFixture.withdraw;
import static woowacourse.fixture.CustomerFixture.login;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUpCustomer() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "test@woowacourse.com");
        requestBody.put("nickname", "test");
        requestBody.put("password", "test123!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("중복된 아이디로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpDuplicateUserId() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "puterism@woowacourse.com");
        requestBody.put("nickname", "test");
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 가입된 이메일입니다."));
    }

    @DisplayName("중복된 닉네임으로 회원가입을 하면 예외가 발생한다.")
    @Test
    void signUpDuplicateNickname() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "test@woowacourse.com");
        requestBody.put("nickname", "nickname");
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 존재하는 닉네임입니다."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ddfkdksfk", "ddddddddddnaver.com", "cdd@dd"})
    @DisplayName("잘못된 아이디 형식으로 회원가입을 하면 예외가 발생한다.")
    void signUpInvalidFormatUserId(final String userId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("nickname", "nickname");
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("아이디는 이메일 형식으로 입력해주세요."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@#$%", "a", "aaaaaaaaaaa"})
    @DisplayName("잘못된 닉네임 형식으로 회원가입을 하면 예외가 발생한다.")
    void signUpInvalidFormatNickname(final String nickname) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "test@woowacourse.com");
        requestBody.put("nickname", nickname);
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234!@#$", "1234asdf", "asdf!@#$", "a", "aaaaaaaaaaaaaaaaa"})
    @DisplayName("잘못된 비밀번호 형식으로 회원가입을 하면 예외가 발생한다.")
    void signUpInvalidFormatPassword(final String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "test@woowacourse.com");
        requestBody.put("nickname", "test");
        requestBody.put("password", password);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요."));
    }

    @DisplayName("로그인을 한다.")
    @Test
    void loginCustomer() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "puterism@woowacourse.com");
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("userId", is("puterism@woowacourse.com"))
                .body("nickname", is("nickname"));
    }

    @DisplayName("존재하지 않은 회원 정보로 로그인하면 안된다.")
    @Test
    void loginNotExistingCustomer() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "test@woowacourse.com");
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/login")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", is("아아디 또는 비밀번호를 확인하여주세요."));
    }

    @DisplayName("비밀번호가 틀리면 로그인이 안된다.")
    @Test
    void loginInvalidPassword() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "puterism@woowacourse.com");
        requestBody.put("password", "invalidPassword");

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/login")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요."));
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        ExtractableResponse<Response> secondResponse = findById(token);

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("userId", is("puterism@woowacourse.com"))
                .body("nickname", is("nickname"));
    }

    @DisplayName("탈퇴한 회원의 정보 조회를 하면 안된다.")
    @Test
    void getMeWithdrawal() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        // when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("존재하지 않는 회원입니다."));
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nickname", "리버");
        requestBody.put("password", "1234asdf!");

        // when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않는 사용자가 내 정보 수정을 요청하면 안된다.")
    @Test
    void updateMeNotExistingCustomer() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nickname", "리버");
        requestBody.put("password", "1234asdf!");

        // when & than
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("존재하지 않는 회원입니다."));
    }

    @DisplayName("이미 존재하는 닉네임으로 내 정보 수정을 요청하면 안된다.")
    @Test
    void updateMeDuplicateNickname() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nickname", "nickname");
        requestBody.put("password", "1234asdf!");

        // when & than
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 존재하는 닉네임입니다."));
    }

    @DisplayName("비밀번호 수정")
    @Test
    void updateMePassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("oldPassword", "1234asdf!");
        requestBody.put("newPassword", "1234asdf!");

        // when & than
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("기존 비밀번호가 일치하지 않는 경우 비밀번호 변경이 안된다.")
    @Test
    void updateMeInvalidPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("oldPassword", "asdf1234!");
        requestBody.put("newPassword", "asdf1234!");

        // when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("아아디 또는 비밀번호를 확인하여주세요."));
    }

    @DisplayName("수정하려는 비밀번호가 올바른 형식이 아닐 경우 비밀번호 변경이 안된다.")
    @Test
    void updateMeInvalidPasswordFormat() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("oldPassword", "1234asdf!");
        requestBody.put("newPassword", "invalidPassword");

        // when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요."));
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when & then
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("탈퇴한 사용자가 탈퇴를 요청할 경우 안된다.")
    @Test
    void deleteMeWithdrawal() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        // when & then
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", "1234asdf!");

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("존재하지 않는 회원입니다."));
    }

    @DisplayName("탈퇴를 요청할 경우 입력한 비밀번호가 실제 비밀번호와 일치하지 않으면 안된다.")
    @Test
    void deleteMeWithdrawalNotMatchPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when & then
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", "1234asdwqwqf!");

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("아아디 또는 비밀번호를 확인하여주세요."));
    }

    @DisplayName("회원가입시 기존에 존재하는 아이디를 입력하면 안된다.")
    @Test
    void checkDuplicateUserId_exception() {
        // when & then
        RestAssured
                .given().log().all()
                .when().get("/customers/check?userId=puterism@woowacourse.com")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 가입된 이메일입니다."));
    }

    @DisplayName("회원가입시 기존에 존재하는 닉네임을 입력하면 안된다.")
    @Test
    void checkDuplicateNickname_exception() {
        // when & then
        RestAssured
                .given().log().all()
                .when().get("/customers/check?nickname=nickname")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 존재하는 닉네임입니다."));
    }

    @DisplayName("회원가입시 기존에 존재하지 않는 닉네임을 입력하면 상태코드 200을 반환한다.")
    @Test
    void checkDuplicateNickname() {
        // when & then
        RestAssured
                .given().log().all()
                .when().get("/customers/check?nickname=새로운이름")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원가입시 기존에 존재하지 않는 아이디를 입력하면 상태코드 200을 반환한다.")
    @Test
    void checkDuplicateUserId() {
        // when & then
        RestAssured
                .given().log().all()
                .when().get("/customers/check?userId=coobim@woowacourse.com")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
