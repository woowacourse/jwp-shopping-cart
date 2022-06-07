package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.dto.MemberRegisterRequest;

public enum TMember {

    MARU("maru@gmai.com", "Maru1234!", "송채원"),
    HUNI("huni@gmail.com", "Huni1234!", "최재훈");

    private static final Request REQUEST = new Request();

    private final String email;
    private final String password;
    private final String name;

    private String token;

    TMember(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public ExtractableResponse<Response> validateDuplicateEmail() {
        return REQUEST.get("/api/members/duplicate-email?email=" + email);
    }

    public ExtractableResponse<Response> validateDuplicateEmailWrongFormat() {
        return REQUEST.get("/api/members/duplicate-email?email=" + "wrong");
    }

    public ExtractableResponse<Response> register() {
        return REQUEST.post(new MemberRegisterRequest(email, password, name), "/api/members");
    }

    public ExtractableResponse<Response> failedRegister() {
        return REQUEST.post(new MemberRegisterRequest("", "", ""), "/api/members");
    }

    public TokenResponse login() {
        if (Objects.nonNull(token)) {
            return new TokenResponse(token);
        }
        ExtractableResponse<Response> response = REQUEST.post(new TokenRequest(email, password), "/api/auth");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        token = tokenResponse.getAccessToken();
        return tokenResponse;
    }

    public ErrorResponse failedLogin(String wrongPassword) {
        ExtractableResponse<Response> response = REQUEST.post(new TokenRequest(email, wrongPassword), "/api/auth");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    public LoginAnd loginAnd() {
        return new LoginAnd(this);
    }

    public NoLoginAnd NoLoginAnd() {
        return new NoLoginAnd();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
