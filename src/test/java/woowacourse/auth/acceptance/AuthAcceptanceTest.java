package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredFixture;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer 토큰이 생성된다")
    @Test
    void createToken() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "users", 201);

        // when
        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        // then
        assertThat(token).isNotBlank();
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        RestAssuredFixture.get(token, "/users/me", HttpStatus.OK.value())
                .body("username", is("rennon"))
                .body("email", is("rennon@woowa.com"));
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        // then
        // 토큰 발급 요청이 거부된다
        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123578");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        // then
        // 내 정보 조회 요청이 거부된다
        RestAssuredFixture.get("dummy", "/users/me", HttpStatus.UNAUTHORIZED.value());
    }
}
