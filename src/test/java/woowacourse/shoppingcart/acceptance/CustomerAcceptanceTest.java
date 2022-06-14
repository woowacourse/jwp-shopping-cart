package woowacourse.shoppingcart.acceptance;

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
import woowacourse.shoppingcart.dto.EmailValidationRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("회원 가입 성공하면 상태코드 201과 저장된 회원 정보를 반환한다.")
    @Test
    void registerCustomers() {
        // when
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = postWithBody("/customers", customerRequest);
        // then
        CustomerResponse customerResponse = registerCustomerResponse.body().as(CustomerResponse.class);

        assertAll(
                () -> assertThat(registerCustomerResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo(NAME),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("토큰을 활용해 회원 정보 수정을 요청하면 수정된 회원 정보를 조회할 수 있다.")
    @Test
    void editCustomerInformation() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = postWithBody("/auth/login", tokenRequest).as(TokenResponse.class).getAccessToken();
        // when
        CustomerRequest updateCustomerRequest = new CustomerRequest(EMAIL, PASSWORD, "bani", PHONE, ADDRESS);
        putByTokenWithBody("/customers", accessToken, updateCustomerRequest);
        // then
        CustomerResponse customerResponse = getByToken("/customers", accessToken).as(CustomerResponse.class);
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo("bani"),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("토큰을 활용해 회원 정보 삭제를 요청하면 상태 코드 204를 반환한다.")
    @Test
    void deleteCustomer() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = postWithBody("/auth/login", tokenRequest).as(TokenResponse.class).getAccessToken();
        // when
        ExtractableResponse<Response> response = deleteWithToken("/customers", accessToken);
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("Bearer Auth : 유효하지 않은 토큰을 통해 정보 조회를 시도하면 정보 조회가 거부되며 상태 코드 401을 반환한다.")
    @Test
    void findCustomerByToken_WhenIllegalToken() {
        // given
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = postWithBody("/auth/login", tokenRequest).as(TokenResponse.class).getAccessToken();
        // when
        ExtractableResponse<Response> customerResponse = getByToken("/customers", accessToken + "l");
        // then
        assertAll(
                () -> assertThat(customerResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(customerResponse.jsonPath().getString("message")).isEqualTo("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("회원 가입 전 이메일 검증 시 중복된 이메일으로 검증 요청을 보낸다면 상태코드 400을 반환한다.")
    @Test
    void validateDuplicateEmail() {
        // when
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = postWithBody("/customers", customerRequest);
        CustomerResponse customerResponse = registerCustomerResponse.body().as(CustomerResponse.class);
        // then
        ExtractableResponse<Response> response = postWithBody("/customers/email/validate",
                new EmailValidationRequest(customerRequest.getEmail()));
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @DisplayName("회원 가입 전 이메일 검증 시 신규 이메일로 검증 요청을 보낸다면 상태코드 200을 반환한다.")
    @Test
    void validateEmail() {
        // when
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        // then
        ExtractableResponse<Response> response = postWithBody("/customers/email/validate",
                new EmailValidationRequest(customerRequest.getEmail()));
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
