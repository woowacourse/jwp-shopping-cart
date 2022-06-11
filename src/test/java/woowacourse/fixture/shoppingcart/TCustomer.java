package woowacourse.fixture.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.auth.ui.dto.TokenRequest;
import woowacourse.global.exception.ErrorResponse;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

public enum TCustomer {

    ROOKIE("rookie@gmail.com", "Qwer123!@", "rookie"),
    ZERO("zero@gmail.com", "Qwer123!@", "zero");

    private final String email;
    private final String password;
    private final String nickname;

    private String accessToken;

    TCustomer(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public ExtractableResponse<Response> signUp() {
        return post("/api/customers", new CustomerSignUpRequest(email, password, nickname));
    }

    public ErrorResponse signUpDuplicatedOfEmail() {
        ROOKIE.signUp();
        ExtractableResponse<Response> response = post(
                "/api/customers",
                new CustomerSignUpRequest(email, password, "nickname"));

        return response.as(ErrorResponse.class);
    }

    public ErrorResponse signUpDuplicatedOfNickname() {
        ROOKIE.signUp();
        ExtractableResponse<Response> response = post(
                "/api/customers",
                new CustomerSignUpRequest("email@gmail.com", password, nickname));

        return response.as(ErrorResponse.class);
    }

    public TokenResponse signIn() {
        ExtractableResponse<Response> response = post("/api/login", new TokenRequest(email, password));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        TokenResponse tokenResponse = response.as(TokenResponse.class);
        this.accessToken = tokenResponse.getAccessToken();
        return tokenResponse;
    }

    public ErrorResponse signInFailed() {
        ExtractableResponse<Response> response = post("/api/login", new TokenRequest("email", password));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    public ErrorResponse signInWrongToken() {
        ExtractableResponse<Response> response = post("/api/customers/me", "WrongToken");

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

    public LoginAnd signInAnd() {
        return new LoginAnd(this);
    }

    public NoLoginAnd noSignInAnd() {
        return new NoLoginAnd(this);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
