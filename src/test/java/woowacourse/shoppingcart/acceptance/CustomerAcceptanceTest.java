package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "crew01@naver.com";
    private static final String VALID_PASSWORD = "a12345";
    private static final SignInRequest SIGN_IN_REQUEST = new SignInRequest(EMAIL, VALID_PASSWORD);
    private static final String VALID_USERNAME = "puterism";
    private static final String INVALID_PASSWORD = "a123456";
    private static final String NEW_PASSWORD = "123456";
    private static final String NEW_USERNAME = "alpha";
    private static final String NEW_EMAIL = "bcc0830@naver.com";

    @Test
    void 회원가입() {
        SignUpRequest signUpRequest = new SignUpRequest(NEW_USERNAME, NEW_EMAIL, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest).as(SignUpResponse.class);

        assertAll(
                () -> assertThat(extract.getUsername()).isEqualTo(NEW_USERNAME),
                () -> assertThat(extract.getEmail()).isEqualTo(NEW_EMAIL)
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_이메일이_공백인_경우(String invalidEmail) {
        SignUpRequest signUpRequest = new SignUpRequest(NEW_USERNAME, invalidEmail, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains("[ERROR]", "이메일");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom", "bcc0830@naver. com", "칙쵹@naver.com"})
    void 회원가입시_올바른_이메일_형식이_아닌_경우(String invalidEmail) {
        SignUpRequest signUpRequest = new SignUpRequest(NEW_USERNAME, invalidEmail, NEW_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 올바른 이메일 형식이 아닙니다.");
    }

    @Test
    void 회원가입시_이메일_길이가_64를_초과하는_경우() {
        SignUpRequest signUpRequest = new SignUpRequest(
                NEW_USERNAME,
                "0123456789012345678901234567890123456789012345678901234@naver.com",
                NEW_PASSWORD
        );

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이메일의 길이는 64자를 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_이름이_공백인_경우(String invalidName) {
        SignUpRequest signUpRequest = new SignUpRequest(invalidName, NEW_EMAIL, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains("[ERROR]", "이름");
    }

    @Test
    void 회원가입시_이름에_공백이_포함되는_경우() {
        SignUpRequest signUpRequest = new SignUpRequest("1 2", NEW_EMAIL, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이름에는 공백이 포함될 수 없습니다.");
    }

    @Test
    void 회원가입시_이름이_32자를_초과하는_경우() {
        SignUpRequest signUpRequest = new SignUpRequest("0123456789012345678901234567890123", NEW_EMAIL,
                VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이름의 길이는 32자를 초과할 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원가입시_비밀번호가_공백인_경우(String invalidPassword) {
        SignUpRequest signUpRequest = new SignUpRequest(NEW_USERNAME, NEW_EMAIL, invalidPassword);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains("[ERROR]", "비밀번호");
    }

    @Test
    void 회원가입시_비밀번호가_6자_미만_경우() {
        SignUpRequest signUpRequest = new SignUpRequest(NEW_USERNAME, NEW_EMAIL, "12345");

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"칙촉1234,한글", "a b1234,공백"})
    void 회원가입시_비밀번호에_한글_또는_공백이_포함되는_경우(String invalidPassword, String message) {
        var signUpRequest = new SignUpRequest(NEW_USERNAME, NEW_EMAIL, invalidPassword);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains(message);
    }

    @Test
    void 회원가입시_이미_존재하는_이메일인_경우() {
        var signUpRequest = new SignUpRequest(NEW_USERNAME, EMAIL, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이미 존재하는 이메일입니다.");
    }

    @Test
    void 회원가입시_이미_존재하는_사용자이름인_경우() {
        SignUpRequest signUpRequest = new SignUpRequest(VALID_USERNAME, EMAIL, VALID_PASSWORD);

        var extract = createSignUpResult(signUpRequest);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이미 존재하는 사용자 이름입니다.");
    }

    @Test
    void 유효한_토큰으로_정보를_조회하는_경우() {
        var accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        var customerResponse = createCustomerInformation(accessToken, HttpStatus.OK)
                .as(CustomerResponse.class);

        assertAll(
                () -> assertThat(customerResponse.getUsername()).isEqualTo(VALID_USERNAME),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL)
        );
    }

    @Test
    void 유효기간이_지난_토큰으로_정보를_조회하는_경우() {
        String invalidToken = generatedExpiredToken();

        var response = createCustomerInformation(invalidToken, HttpStatus.UNAUTHORIZED);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 만료된 토큰입니다.");
    }

    private static String generatedExpiredToken() {
        Date now = new Date(0L);
        Date validity = new Date(1L);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 비밀번호_변경_시_기존_비밀번호가_공백인_경우(String invalidPassword) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK)
                .as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(invalidPassword, INVALID_PASSWORD);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains("[ERROR]", "비밀번호");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 비밀번호_변경_시_새로운_비밀번호가_공백인_경우(String invalidPassword) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(VALID_PASSWORD, invalidPassword);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains("[ERROR]", "비밀번호");
    }

    @Test
    void 비밀번호_변경_시_기존_비밀번호를_6자_미만으로_입력한_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("a1234", VALID_PASSWORD);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
    }

    @Test
    void 비밀번호_변경_시_바꿀_비밀번호가_6자_미만인_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(VALID_PASSWORD, "a1234");

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"칙촉1234,한글", "a b1234,공백"})
    void 비밀번호_변경_시_바꿀_비밀번호에_한글_또는_공백이_포함된_경우(String invalidPassword, String message) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(VALID_PASSWORD, invalidPassword);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains(message);
    }

    @ParameterizedTest
    @CsvSource(value = {"칙촉1234,한글", "a b1234,공백"})
    void 비밀번호_변경_시_기존_비밀번호에_한글_또는_공백이_포함된_경우(String invalidPassword, String message) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(invalidPassword, VALID_PASSWORD);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains(message);
    }

    @Test
    void 유효기간이_지난_토큰으로_비밀번호를_변경하는_경우() {
        String invalidToken = generatedExpiredToken();

        var response = createChangePasswordResult(invalidToken, new ChangePasswordRequest(VALID_PASSWORD, NEW_PASSWORD),
                HttpStatus.UNAUTHORIZED);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 만료된 토큰입니다.");
    }

    @Test
    void 비밀번호_수정_시_기존_비밀번호가_다른_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(INVALID_PASSWORD, NEW_PASSWORD);

        var response = createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 비밀번호_수정() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(VALID_PASSWORD, INVALID_PASSWORD);

        createChangePasswordResult(accessToken, changePasswordRequest, HttpStatus.OK);

        var response = createSignInResult(SIGN_IN_REQUEST, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호가 일치하지 않습니다.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 회원탈퇴시_비밀번호가_공백인_경우(String invalidPassword) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(invalidPassword);

        var response = createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains("[ERROR]", "비밀번호");
    }

    @Test
    void 회원탈퇴시_비밀번호가_6자_미만인_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("a1234");

        var response = createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"칙촉1234,한글", "a b1234,공백"})
    void 회원탈퇴시_비밀번호에_한글_또는_공백이_포함된_경우(String invalidPassword, String message) {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(invalidPassword);

        var response = createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .contains(message);
    }

    @Test
    void 유효기간이_지난_토큰으로_회원탈퇴_하는_경우() {
        String invalidToken = generatedExpiredToken();

        var response = createDeleteCustomerResult(invalidToken, new DeleteCustomerRequest(VALID_PASSWORD),
                HttpStatus.UNAUTHORIZED);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 만료된 토큰입니다.");
    }

    @Test
    void 회원탈퇴시_기존_비밀번호가_다른_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest(INVALID_PASSWORD);

        var response = createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원탈퇴시_기존_비밀번호에_한글이_포함된_경우_다른_경우() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("칙촉1234");

        var response = createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호에 한글이 포함될수 없습니다.");
    }

    @Test
    void 회원탈퇴() {
        String accessToken = createSignInResult(SIGN_IN_REQUEST, HttpStatus.OK).as(SignInResponse.class).getToken();

        var deleteCustomerRequest = new DeleteCustomerRequest(VALID_PASSWORD);

        createDeleteCustomerResult(accessToken, deleteCustomerRequest, HttpStatus.NO_CONTENT);

        var response = createSignInResult(SIGN_IN_REQUEST, HttpStatus.BAD_REQUEST);

        assertThat(response.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 이메일 입니다.");
    }
}
