package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.코린;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.PhoneNumberFormat;
import woowacourse.shoppingcart.dto.SignupRequest;

public class SignupAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setup() {
        post("/signup", 에덴);
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void signup() {
        // when
        final ExtractableResponse<Response> response = post("/signup", 코린);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("location")).isEqualTo("/signin")
        );
    }

    @Test
    @DisplayName("회원가입 시 중복된 아이디로 회원가입 요청 시 400 상태코드를 반환한다.")
    void signupWithDuplicated() {
        // when
        final ExtractableResponse<Response> response = post("/signup", 에덴);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("이미 존재하는 아이디입니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"123", "1234567890123456"})
    @DisplayName("회원가입 시 아이디 길이가 4~15자를 벗어나면 400 상태코드를 반환한다.")
    void invalidAccountLength(String account) {
        // given
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "Password123!", "address",
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("아이디 길이는 4~15자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"YEONLOG,yeonlog", "aa_01,aa01"})
    @DisplayName("회원가입 시 아이디가 대문자이면 소문자로 변경하고 특수문자가 들어가면 제거한다.")
    void changeAccountPattern(String account, String expectedAccount) {
        // given
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "Password123!", "address",
                new PhoneNumberFormat("010", "1234", "5678"));
        post("/signup", signupRequest);

        // when
        final SignupRequest duplicatedAccountSignupRequest = new SignupRequest(expectedAccount, "eden", "Password123!",
                "address", new PhoneNumberFormat("010", "1234", "5678"));
        final ExtractableResponse<Response> response = post("/signup", duplicatedAccountSignupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("이미 존재하는 아이디입니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"에덴짱123"})
    @DisplayName("회원가입 시 아이디에 한글이 포함되면 400 상태코드를 반환한다.")
    void notAcceptedKoreanAccount(String account) {
        // given
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "Password123!", "address",
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("한글 아이디는 허용되지 않습니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "12345678901"})
    @DisplayName("회원가입 시 닉네임 길이가 2~10자를 벗어나면 400 상태코드를 반환한다.")
    void invalidNicknameLength(String nickName) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", nickName, "Password123!", "address",
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("닉네임 길이는 2~10자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"Abc123!", "Abc123abc123abc123abc123!!"})
    @DisplayName("회원가입 시 비밀번호 길이가 8~20자를 벗어나면 400 상태코드를 반환한다.")
    void invalidPasswordLength(String password) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address",
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("비밀번호 길이는 8~20자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"12345678aa", "aA!!!!Aa", "korinnee123", "qwe123!!!", "tjdtksdlWkd"})
    @DisplayName("회원가입 시 비밀번호가 영어 대문자, 소문자, 숫자 중 2종류 이상을 조합하지 않았다면 상태코드 400을 반환한다.")
    void invalidPasswordPattern(String password) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address",
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(
                        "비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.")
        );
    }

    @Test
    @DisplayName("회원가입 시 주소의 길이가 255자를 초과하면 상태코드 400을 반환한다.")
    void invalidAddressLength() {
        // given
        String address = "a".repeat(256);
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", "Password123!", address,
                new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("주소 길이는 255자를 초과할 수 없습니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"01,1234,1234", "0101,1234,1234", "010,123,1234", "010,12345,1234", "010,1234,123",
            "010,1234,12345"})
    @DisplayName("회원가입 시 휴대폰 번호의 각각 길이가 3, 4, 4자가 아니면 상태코드 400을 반환한다.")
    void invalidPhoneNumberLength(String start, String middle, String end) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", "Password123!", "address",
                new PhoneNumberFormat(start, middle, end));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("휴대폰번호 형식이 일치하지 않습니다.")
        );
    }
}
