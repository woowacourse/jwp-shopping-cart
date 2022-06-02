package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.SignInRequest;
import woowacourse.shoppingcart.dto.SignInResponse;

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
    @ValueSource(strings = {"", " "})
    void 로그인_이메일이_공백인_경우(String invalidEmail) {
        SignInRequest signInRequest = new SignInRequest(invalidEmail, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains("[ERROR]", "이메일");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@naver.com", "bcc0830naver.com", "bcc0830@", "bcc0830", "bcc0830#naver.com",
            "bcc0830@navercom", "칙@naver.com", "qnfrrmfo@n aver.com"})
    void 로그인시_이메일_형식이_아닌_경우(String invalidEmail) {
        SignInRequest signInRequest = new SignInRequest(invalidEmail, VALID_PASSWORD);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 올바른 이메일 형식이 아닙니다.");
    }

    @Test
    void 로그인시_이메일의_길이가_64자_초과인_경우() {
        SignInRequest signInRequest = new SignInRequest(
                "0123456789012345678901234567890123456789012345678901234@naver.com",
                VALID_PASSWORD
        );

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 이메일의 길이는 64자를 넘을수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void 로그인시_비밀번호가_공백인_경우(String invalidPassword) {
        SignInRequest signInRequest = new SignInRequest(NEW_EMAIL, invalidPassword);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .contains("[ERROR]", "비밀번호");
    }

    @Test
    void 로그인시_비밀번호가_6자_미만인_경우() {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, "a1234");

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호는 6자 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"칙촉1234", "a b1234"})
    void 로그인시_비밀번호에_한글이_포함된_경우(String invalidPassword) {
        SignInRequest signInRequest = new SignInRequest(VALID_EMAIL, invalidPassword);

        var extract = createSignInResult(signInRequest, HttpStatus.BAD_REQUEST);

        assertThat(extract.body().jsonPath().getString("message"))
                .isEqualTo("[ERROR] 비밀번호는 한글이나 공백을 포함할 수 없습니다.");
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
}
