package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("Bearer Auth : 회원 로그인에 성공하면 Token을 통해 회원 정보를 조회할 수 있다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = postWithBody("/auth/login", tokenRequest)
                .as(TokenResponse.class)
                .getAccessToken();
        // when
        CustomerResponse customerResponse = getByToken("/customers", accessToken).as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo(NAME),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("Bearer Auth 로그인 : 일치하지 않는 이메일을 통해 로그인 시도를 하면 토큰 발급 요청이 거부되며 상태 코드 400이 반환된다.")
    @Test
    void loginCustomer_WhenInvalidEmail() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);
        // when
        TokenRequest tokenRequest = new TokenRequest("test@test.com", PASSWORD);
        ExtractableResponse<Response> loginResponse = postWithBody("/auth/login", tokenRequest);
        // then
        assertAll(
                () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(loginResponse.jsonPath().getString("message")).isEqualTo("존재하지 않는 이메일 입니다.")
        );
    }

    @DisplayName("Bearer Auth 로그인 : 일치하지 않는 비밀번호를 통해 로그인 시도를 하면 토큰 발급 요청이 거부되며 상태 코드 400이 반환된다.\"")
    @Test
    void loginCustomer_WhenInvalidPassword() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);
        // when
        TokenRequest tokenRequest = new TokenRequest(EMAIL, "Bunny1234!");
        ExtractableResponse<Response> loginResponse = postWithBody("/auth/login", tokenRequest);
        // then
        assertAll(
                () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(loginResponse.jsonPath().getString("message")).isEqualTo("비밀번호가 일치하지 않습니다.")
        );
    }
}
