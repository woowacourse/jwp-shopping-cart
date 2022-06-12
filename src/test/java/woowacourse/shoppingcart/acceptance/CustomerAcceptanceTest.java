package woowacourse.shoppingcart.acceptance;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.SignInResponseDto;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));

        assertAll(
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isNotBlank(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("이미 가입된 이메일로 회원가입을 요청한다.")
    @Test
    void addCustomer_duplicateEmail() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, TEST_PASSWORD, "테스트2"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일 형식이 아닌 형태로 회원가입을 요청시 400을 반환한다.")
    @Test
    void addCustomer_blankEmail() {
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto("test@te st.com", TEST_PASSWORD, TEST_USERNAME));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호의 길이가 8자 미만이거나 20자 초과일 경우 400을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234567", "testtesttesttesttestt"})
    void addCustomer_invalidLengthPassword(final String password) {
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, password, TEST_USERNAME));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 공백으로만 이루어졌을 경우 400을 반환한다.")
    @Test
    void addCustomer_blankPassword() {
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto(TEST_EMAIL, "        ", TEST_USERNAME));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이미 가입된 닉네임으로 회원가입을 요청한다.")
    @Test
    void addCustomer_duplicateUsername() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto("test2@test.com", TEST_PASSWORD, TEST_USERNAME));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("공백이 있는 닉네임으로 회원가입을 요청한다.")
    @Test
    void addCustomer_blankUsername() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto("test2@test.com", TEST_PASSWORD, "  te st"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("10글자 이상의 닉네임으로 회원가입을 요청한다.")
    @Test
    void addCustomer_tooLongUsername() {
        createCustomer(new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        final ExtractableResponse<Response> response = createCustomer(
                new SignUpDto("test2@test.com", TEST_PASSWORD, "testtesttes"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final SignInResponseDto signInResponseDto = loginResponse.body().as(SignInResponseDto.class);

        final ExtractableResponse<Response> customerResponse = get(createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + signInResponseDto.getAccessToken()));

        final CustomerDto customerDto = customerResponse.body().as(CustomerDto.class);

        assertAll(
                () -> assertThat(customerDto.getEmail()).isEqualTo(TEST_EMAIL),
                () -> assertThat(customerDto.getUsername()).isEqualTo(TEST_USERNAME)
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final SignInResponseDto signInResponseDto = loginResponse.body().as(SignInResponseDto.class);

        final String updateUsername = "테스트2";
        final ExtractableResponse<Response> updateResponse = put(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + signInResponseDto.getAccessToken()),
                new UpdateCustomerDto(updateUsername)
        );

        final CustomerDto updateCustomer = updateResponse.body().as(CustomerDto.class);
        assertThat(updateCustomer.getUsername()).isEqualTo(updateUsername);
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> createResponse = createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final SignInResponseDto signInResponseDto = loginResponse.body().as(SignInResponseDto.class);

        final ExtractableResponse<Response> deleteResponse = post(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + signInResponseDto.getAccessToken()),
                new DeleteCustomerDto(TEST_PASSWORD)
        );

        final ExtractableResponse<Response> customerResponse = get(
                createResponse.header(HttpHeaders.LOCATION),
                new Header(HttpHeaders.AUTHORIZATION, BEARER + signInResponseDto.getAccessToken())
        );

        assertAll(
                () -> assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(customerResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())
        );
    }
}
