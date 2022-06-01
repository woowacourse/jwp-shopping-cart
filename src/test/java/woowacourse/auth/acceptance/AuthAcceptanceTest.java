package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.Fixture.BEARER;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        final String accessToken = tokenResponseDto.getAccessToken();

        // when
        final ExtractableResponse<Response> response = get(
                "/api/customers/" + tokenResponseDto.getCustomer().getId(),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
        );

        // then
        final CustomerDto actual = response.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(actual.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(actual.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        // when
        final ExtractableResponse<Response> wrongPasswordResponse = loginCustomer(TEST_EMAIL, "wrongPassword");

        // then
        assertThat(wrongPasswordResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        // when
        final ExtractableResponse<Response> response = get(
                "/api/customers/" + tokenResponseDto.getCustomer().getId(),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + "invalidToken")
        );

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
