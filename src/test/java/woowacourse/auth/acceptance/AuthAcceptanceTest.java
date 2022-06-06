package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.에덴;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        post("/signup", 에덴);
        // id, password를 사용해 토큰을 발급받고
        final ExtractableResponse<Response> tokenResponse = post("/signin",
                new TokenRequest("leo0842", "Password123!"));

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        final TokenResponse token = tokenResponse.jsonPath().getObject(".", TokenResponse.class);
        final ExtractableResponse<Response> response = get("/customers", token.getAccessToken());
        final CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getId()).isEqualTo(2L)
        );
    }

    @DisplayName("잘못된 아이디로 인한 Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadId() {
        // given
        // 회원이 등록되어 있고
        post("/signup", 에덴);
        // when
        // 잘못된 id 를 사용해 토큰을 요청하면
        final ExtractableResponse<Response> response = post("/signin", new TokenRequest("eden0842", "Password123!"));

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("ID 또는 비밀번호가 올바르지 않습니다.")
        );
    }

    @DisplayName("잘못된 비밀번호로 인한 Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadPassword() {
        // given
        // 회원이 등록되어 있고
        post("/signup", 에덴);
        // when
        // 잘못된 password 를 사용해 토큰을 요청하면
        final ExtractableResponse<Response> response = post("/signin",
                new TokenRequest("leo0842", "BadPassword123!"));

        // then
        // 토큰 발급 요청이 거부된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("ID 또는 비밀번호가 올바르지 않습니다.")
        );
    }
}
