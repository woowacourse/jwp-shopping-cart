package woowacourse.shoppingcart.acceptance;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.request.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.request.SignUpDto;
import woowacourse.shoppingcart.dto.request.UpdateCustomerDto;
import woowacourse.shoppingcart.dto.response.CustomerDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.Fixture.*;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        //when
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        //then
        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        //given
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        //when
        final ExtractableResponse<Response> customerResponse = get(createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponseDto.getAccessToken()));

        //then
        final CustomerDto customerDto = customerResponse.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(customerDto.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(customerDto.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        //given
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);

        //when
        final String updateUsername = "updateName";
        final ExtractableResponse<Response> updateResponse = put(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponseDto.getAccessToken()),
                new UpdateCustomerDto(updateUsername)
        );

        //then
        final CustomerDto updateCustomer = updateResponse.body().as(CustomerDto.class);
        assertThat(updateCustomer.getUsername()).isEqualTo(updateUsername);
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {

        //given
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        final ExtractableResponse<Response> deleteResponse = post(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponseDto.getAccessToken()),
                new DeleteCustomerDto(TEST_PASSWORD)
        );

        //when
        final ExtractableResponse<Response> customerResponse = get(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + tokenResponseDto.getAccessToken())
        );

        //then
        assertAll(
                () -> assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(customerResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
        );
    }
}
