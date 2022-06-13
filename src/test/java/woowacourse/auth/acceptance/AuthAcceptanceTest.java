package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String VALID_PASSWORD = "a12345";
    private static final String VALID_EMAIL = "crew01@naver.com";
    private static final String VALID_NAME = "puterism";
    private static final String NEW_EMAIL = "bcc0830@naver.com";
    private static final String INVALID_EMAIL = "crew10@naver.com";
    private static final String INVALID_PASSWORD = "a123456";

    @Test
    void 로그인_성공() {
        var signInRequest = new SignInRequest(VALID_EMAIL, VALID_PASSWORD);

        var signInResponse = createSignInResult(signInRequest, HttpStatus.OK).as(SignInResponse.class);

        assertAll(
                () -> assertThat(signInResponse.getUsername()).isEqualTo(VALID_NAME),
                () -> assertThat(signInResponse.getEmail()).isEqualTo(VALID_EMAIL),
                () -> assertThat(signInResponse.getToken()).isNotNull()
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void 로그인시_이메일이_빈_입력이거나_null인_경우(String invalidEmail) {
        SignInRequest signInRequest = new SignInRequest(invalidEmail, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이메일은 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom", "bcc0830@ naver.com", " bcc0830@naver.com", "bcc0830@naver.com "})
    void 로그인시_이메일_형식이_아닌_경우(String invalidEmail) {
        SignInRequest signInRequest = new SignInRequest(invalidEmail, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이메일 형식이 아닙니다.");
    }

    @Test
    void 로그인시_이메일의_길이가_64자_초과인_경우() {
        String invalidEmail = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@naver.com";
        SignInRequest signInRequest = new SignInRequest(invalidEmail, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이메일은 최대 64자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void 로그인시_비밀번호가_빈_입력이거나_null인_경우(String invalidPassword) {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, invalidPassword);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호는 빈 값일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"       ", "ㄱㄴㄷㄹㅁㅂ", "ㅣㅏㅑㅐㅔㅕ", "가낳답갈세댇", "asd   ", "   asd", "as   d"})
    void 로그인시_비밀번호에_한글이나_공백이_있는_경우(String invalidPassword) {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, invalidPassword);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호는 한글이나 공백이 들어갈 수 없습니다.");
    }

    @Test
    void 로그인시_비밀번호가_5자_이하인_경우() {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, "a1234");

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호는 최소 6자 이상이어야 합니다.");
    }

    @Test
    void 존재하지_않는_이메일로_로그인_하는_경우() {
        SignInRequest signInRequest = new SignInRequest(INVALID_EMAIL, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 존재하지 않는 이메일 입니다.");
    }

    @Test
    void 틀린_비밀번호로_로그인_하는_경우() {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, INVALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 자동로그인() {
        var signInRequest = new SignInRequest(VALID_EMAIL, VALID_PASSWORD);
        String accessToken = createSignInResult(signInRequest, HttpStatus.OK).as(SignInResponse.class).getToken();

        SignInResponse extract = autoLogin(accessToken, HttpStatus.OK).as(SignInResponse.class);

        assertAll(() -> assertThat(extract.getUsername()).isEqualTo("puterism"),
                () -> assertThat(extract.getEmail()).isEqualTo(VALID_EMAIL),
                () -> assertThat(extract.getToken()).isNotNull());
    }
}
