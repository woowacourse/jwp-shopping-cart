package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.dto.MemberRegisterRequest;

public enum TMember {

    MARU("maru@gmai.com", "Maru1234!", "송채원"),
    HUNI("huni@gmail.com", "Huni1234!", "최재훈");

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
        return get("/api/members/duplicate-email", email);
    }

    public ExtractableResponse<Response> register() {
        return post("/api/members", new MemberRegisterRequest(email, password, name));
    }

    public TokenResponse login() {
        if (Objects.nonNull(token)) {
            return new TokenResponse(token);
        }
        ExtractableResponse<Response> response = post("/api/auth", new TokenRequest(email, password));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        TokenResponse tokenResponse = response
                .as(TokenResponse.class);
        token = tokenResponse.getAccessToken();
        return tokenResponse;
    }

    public LoginAnd loginAnd() {
        return new LoginAnd(this);
    }

    public ErrorResponse failedLogin(String wrongPassword) {
        ExtractableResponse<Response> response = post("/api/auth", new TokenRequest(email, wrongPassword));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }


    private ExtractableResponse<Response> post(String url, Object params) {
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> get(String url, String param) {
        return RestAssured.given().log().all()
                .param("email", param)
                .when()
                .get(url)
                .then().log().all()
                .extract();
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

    public NoLoginAnd noLoginAnd() {
        return new NoLoginAnd(this);
    }
}
