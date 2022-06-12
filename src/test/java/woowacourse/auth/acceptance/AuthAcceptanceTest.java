package woowacourse.auth.acceptance;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.request.SignUpDto;
import woowacourse.shoppingcart.dto.response.CustomerDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.Fixture.*;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인을 한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        final ExtractableResponse<Response> createResponse = createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        final String accessToken = tokenResponseDto.getAccessToken();

        // when
        final ExtractableResponse<Response> response = get(createResponse.header(HttpHeaders.LOCATION), new Header(HttpHeaders.AUTHORIZATION, BEARER + accessToken));

        // then
        final CustomerDto actual = response.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(actual.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("Bearer Auth 로그인을 할때 패스워드가 일치하지 않으면 예외가 발생한다.")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        // when
        final ExtractableResponse<Response> wrongPasswordResponse = loginCustomer(TEST_EMAIL, "wrongPassword");

        // then
        assertThat(wrongPasswordResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 로그인시 유효하지 않은 토큰이 들어오면 예외가 발생한다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        final ExtractableResponse<Response> createResponse
                = createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        loginCustomer(TEST_EMAIL, TEST_PASSWORD);

        // when
        final ExtractableResponse<Response> response
                = get(createResponse.header(HttpHeaders.LOCATION), new Header(HttpHeaders.AUTHORIZATION, "invalidToken"));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
