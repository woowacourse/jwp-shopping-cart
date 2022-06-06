package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.*;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PhoneNumberFormat;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.코린;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

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
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "Password123!", "address", new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("아이디 길이는 4~15자를 만족해야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"YEONLOG", "aa_01"})
    @DisplayName("회원가입 시 아이디가 소문자 혹은 숫자 조합을 만족하지 않으면 상태코드 400을 반환한다.")
    void changeAccountPattern(String account) {
        // when
        final SignupRequest signupRequest = new SignupRequest(account, "eden", "Password123!", "address", new PhoneNumberFormat("010", "1234", "5678"));
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("아이디는 영어 혹은 숫자의 조합으로 이루어져야 합니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "12345678901"})
    @DisplayName("회원가입 시 닉네임 길이가 2~10자를 벗어나면 400 상태코드를 반환한다.")
    void invalidNicknameLength(String nickName) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", nickName, "Password123!", "address", new PhoneNumberFormat("010", "1234", "5678"));

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
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address", new PhoneNumberFormat("010", "1234", "5678"));

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
    @DisplayName("회원가입 시 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함하지 않는다면 상태코드 400을 반환한다.")
    void invalidPasswordPattern(String password) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", password, "address", new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.")
        );
    }

    @Test
    @DisplayName("회원가입 시 주소의 길이가 255자를 초과하면 상태코드 400을 반환한다.")
    void invalidAddressLength() {
        // given
        String address = "a".repeat(256);
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", "Password123!", address, new PhoneNumberFormat("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("주소 길이는 255자를 초과할 수 없습니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"01,1234,1234", "0101,1234,1234", "010,123,1234", "010,12345,1234", "010,1234,123", "010,1234,12345"})
    @DisplayName("회원가입 시 휴대폰 번호의 각각 길이가 3, 4, 4자가 아니면 상태코드 400을 반환한다.")
    void invalidPhoneNumberLength(String start, String middle, String end) {
        // given
        final SignupRequest signupRequest = new SignupRequest("account", "nickname", "Password123!", "address", new PhoneNumberFormat(start, middle, end));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("휴대폰번호 형식이 일치하지 않습니다.")
        );
    }

    @Test
    @DisplayName("회원 정보 조회에 성공한다.")
    void getCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final ExtractableResponse<Response> response = get("/customers", token.getAccessToken());
        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getId()).isEqualTo(2L)
        );
    }

    @Test
    @DisplayName("회원 정보를 조회할 때 헤더에 토큰이 존재하지 않으면 상태코드 401을 반환한다.")
    void tokenNotExistWhenGetCustomer() {
        // given

        // when
        final ExtractableResponse<Response> response = get("/customers");
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그인 후 사용이 가능합니다.")
        );
    }

    @Test
    @DisplayName("회원 정보를 조회할 때 잘못된 형식의 토큰이 전달되면 상태코드 401을 반환한다.")
    void invalidTokenFormatWhenGetCustomer() {
        // given
        JwtTokenProvider jwtTokenProvider = new FakeJwtTokenProvider();
        final String invalidToken = jwtTokenProvider.createToken("fake");
        // when
        final ExtractableResponse<Response> response = get("/customers", invalidToken);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("토큰의 페이로드를 찾을 수 없습니다.")
        );
    }

    @Test
    @DisplayName("존재하지 않은 회원 id로 회원 정보 조회 시 상태코드 400을 반환한다.")
    void customerNotExistWhenGetCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        delete("/customers", token.getAccessToken(), new DeleteCustomerRequest("Password123!"));

        // when
        final ExtractableResponse<Response> response = get("/customers", token.getAccessToken());
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("회원을 찾을 수 없습니다.")
        );
    }

    @Test
    @DisplayName("회원 정보 수정에 성공한다.")
    void updateCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("corinne", "코린네", new PhoneNumberFormat("010", "1234", "1234"));
        final ExtractableResponse<Response> response = put("/customers", token.getAccessToken(), updateCustomerRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("회원 정보를 수정할 때 헤더에 토큰이 존재하지 않으면 상태코드 401을 반환한다.")
    void tokenNotExistWhenUpdateCustomer() {
        // given

        // when
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("corinne", "코린네", new PhoneNumberFormat("010", "1234", "1234"));
        final ExtractableResponse<Response> response = put("/customers", updateCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그인 후 사용이 가능합니다.")
        );
    }

    @Test
    @DisplayName("회원 정보를 수정할 때 잘못된 형식의 토큰이 전달되면 상태코드 401을 반환한다.")
    void invalidTokenFormatWhenUpdateCustomer() {
        // given
        JwtTokenProvider jwtTokenProvider = new FakeJwtTokenProvider();
        final String invalidToken = jwtTokenProvider.createToken("fake");

        // when
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("corinne", "코린네", new PhoneNumberFormat("010", "1234", "1234"));
        final ExtractableResponse<Response> response = put("/customers", invalidToken, updateCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("토큰의 페이로드를 찾을 수 없습니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "12345678901"})
    @DisplayName("회원 수정 시 닉네임 길이가 2~10자를 벗어나면 400 상태코드를 반환한다.")
    void invalidNicknameLengthWhenUpdateCustomer(String nickName) {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(nickName, "address", new PhoneNumberFormat("010", "1234", "5678"));
        final ExtractableResponse<Response> response = put("/customers", token.getAccessToken(), updateCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("닉네임 길이는 2~10자를 만족해야 합니다.")
        );
    }

    @Test
    @DisplayName("회원수정 시 주소의 길이가 255자를 초과하면 상태코드 400을 반환한다.")
    void invalidAddressLengthWhenUpdateCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        String address = "a".repeat(256);
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("corinne", address, new PhoneNumberFormat("010", "1234", "5678"));
        final ExtractableResponse<Response> response = put("/customers", token.getAccessToken(), updateCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("주소 길이는 255자를 초과할 수 없습니다.")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"01,1234,1234", "0101,1234,1234", "010,123,1234", "010,12345,1234", "010,1234,123", "010,1234,12345"})
    @DisplayName("회원수정 시 휴대폰 번호의 각각 길이가 3, 4, 4자가 아니면 상태코드 400을 반환한다.")
    void invalidPhoneNumberLengthWhenUpdateCustomer(String start, String middle, String end) {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("corinne", "address", new PhoneNumberFormat(start, middle, end
        ));
        final ExtractableResponse<Response> response = put("/customers", token.getAccessToken(), updateCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("휴대폰번호 형식이 일치하지 않습니다.")
        );
    }

    @Test
    @DisplayName("회원 탈퇴에 성공한다.")
    void deleteCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("Password123!");
        final ExtractableResponse<Response> response = delete("/customers", token.getAccessToken(), deleteCustomerRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("회원 탈퇴 시 비밀번호가 올바르지 않으면 상태코드 400을 반환한다.")
    void wrongPasswordWhenDeleteCustomer() {
        // given
        final ExtractableResponse<Response> tokenResponse = post("/signin", new TokenRequest("leo0842", "Password123!"));
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);

        // when
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("password123!");
        final ExtractableResponse<Response> response = delete("/customers", token.getAccessToken(), deleteCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("비밀번호가 올바르지 않습니다.")
        );
    }

    @Test
    @DisplayName("회원을 탈퇴할 때 헤더에 토큰이 존재하지 않으면 상태코드 401을 반환한다.")
    void tokenNotExistWhenDeleteCustomer() {
        // given

        // when
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("password123!");
        final ExtractableResponse<Response> response = delete("/customers", deleteCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그인 후 사용이 가능합니다.")
        );
    }

    @Test
    @DisplayName("회원을 탈퇴할 때 잘못된 형식의 토큰이 전달되면 상태코드 401을 반환한다.")
    void invalidTokenFormatWhenDeleteCustomer() {
        // given
        JwtTokenProvider jwtTokenProvider = new FakeJwtTokenProvider();
        final String invalidToken = jwtTokenProvider.createToken("fake");

        // when
        final DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("password123!");
        final ExtractableResponse<Response> response = delete("/customers", invalidToken, deleteCustomerRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("토큰의 페이로드를 찾을 수 없습니다.")
        );
    }
}
