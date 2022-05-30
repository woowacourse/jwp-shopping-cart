package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.dto.SignupRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.코린;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

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
    @DisplayName("중복된 아이디로 회원가입 요청 시 400 상태코드를 반환한다.")
    void signupWithDuplicated() {
        // when
        final ExtractableResponse<Response> response = post("/signup", 에덴);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("이미 존재하는 아이디입니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"123", "1234567890123456"})
    @DisplayName("아이디 길이가 4~15자를 벗어나면 400 상태코드를 반환한다.")
    void invalidAccountLength(String account) {
        // given
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "password123", "address", new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("아이디 길이는 4~15자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"YEONLOG,yeonlog", "aa_01,aa01"})
    @DisplayName("아이디가 대문자이면 소문자로 변경하고 특수문자가 들어가면 제거한다.")
    void changeAccountPattern(String account, String expectedAccount) {
        // given
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "password123", "address", new PhoneNumber("010", "1234", "5678"));
        post("/signup", signupRequest);

        // when
        final SignupRequest duplicatedAccountSignupRequest = new SignupRequest(expectedAccount, "eden", "password123", "address", new PhoneNumber("010", "1234", "5678"));
        final ExtractableResponse<Response> response = post("/signup", duplicatedAccountSignupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("이미 존재하는 아이디입니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "123456789012345678901"})
    @DisplayName("닉네임 길이가 2~20자를 벗어나면 400 상태코드를 반환한다.")
    void invalidNicknameLength(String nickName) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", nickName, "password123", "address", new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("닉네임 길이는 2~20자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"abc123", "abc123abc123abc123abc123"})
    @DisplayName("비밀번호 길이가 8~20자를 벗어나면 400 상태코드를 반환한다.")
    void invalidPasswordLength(String password) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address", new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                    () -> assertThat(response.asString()).isEqualTo("비밀번호 길이는 8~20자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"12345678", "abcdefghi", "ABCDEFGHI", "abcd!!!!!", "1234567!!", "ABCDEFG!!"})
    @DisplayName("비밀번호가 영어 대문자, 소문자, 숫자 중 2종류 이상을 조합하지 않았다면 상태코드 400을 반환한다.")
    void invalidPasswordPattern(String password) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address", new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("비밀번호는 영어 대문자, 소문자, 숫자 중 2종류 이상을 조합해야 합니다.")
        );
    }

    @Test
    @DisplayName("주소의 길이가 255자를 초과하면 상태코드 400을 반환한다.")
    void invalidAddressLength() {
        // given
        String address = "a".repeat(256);
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", "password123", address, new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.asString()).isEqualTo("주소 길이는 255자를 초과할 수 없습니다.")
        );
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
}
