package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        assertAll(
                () -> assertThat(response.header("Location")).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        final ExtractableResponse<Response> customerResponse = get(createResponse.header("Location"),
                new Header("Authorization", "Bearer " + tokenResponseDto.getAccessToken()));

        final CustomerDto customerDto = customerResponse.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(customerDto.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(customerDto.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
