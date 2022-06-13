package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.fixture.SimpleResponse;
import woowacourse.fixture.SimpleRestAssured;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.exception.AuthorizationException;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        signUpCustomer();
    }

    @DisplayName("로그인에 성공할 때 토큰을 발급한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        TokenRequest tokenRequest = new TokenRequest("forky", "forky@1234");
        String accessToken = SimpleRestAssured.post("/login", tokenRequest)
                .toObject(TokenResponse.class)
                .getAccessToken();

        // when
        CustomerResponse actual = SimpleRestAssured.getWithToken("/customers/me", accessToken)
                .toObject(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(actual.getUsername()).isEqualTo("forky"),
                () -> assertThat(actual.getPassword()).isEqualTo("forky@1234")
        );
    }

    @DisplayName("로그인에 실패하는 경우는 토큰 발급 요청이 거부된다.")
    @Test
    void myInfoWithBadBearerAuth() {
        //given
        TokenRequest tokenRequest = new TokenRequest("forky", "kth@990303");

        //when
        SimpleResponse response = SimpleRestAssured.post("/login", tokenRequest);

        //then
        response.assertStatus(HttpStatus.BAD_REQUEST);
        response.containsExceptionMessage("비밀번호");
    }

    @DisplayName("유효하지 않은 토큰으로 회원 관련 기능에 접근할 경우 요청이 거부된다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        SimpleRestAssured.getWithToken("/customers/me", "invalidToken")
                .toObject(AuthorizationException.class);
    }

    @DisplayName("토큰 없이 회원 관련 기능에 접근할 경우 요청이 거부된다.")
    @Test
    void myInfoWithNoAuth() {
        SimpleRestAssured.get("/customers/me")
                .toObject(AuthorizationException.class);
    }

    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        SimpleRestAssured.post("/customers", customerRequest);
    }
}
