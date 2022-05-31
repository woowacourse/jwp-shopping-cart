package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.ErrorResponseWithField;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = 회원가입_요청(
                new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).startsWith("/api/customers/")
        );
    }

    @DisplayName("회원가입시 중복된 email로 가입하려는 경우 400 응답을 반환한다.")
    @Test
    void addCustomer_duplicated_email() {
        ExtractableResponse<Response> response = 회원가입_요청(
                new CustomerCreateRequest("puterism@naver.com", "roma", "12345678"));

        ErrorResponseWithField errorResponseWithField = response.as(ErrorResponseWithField.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponseWithField).usingRecursiveComparison()
                .isEqualTo(new ErrorResponseWithField("email", "이미 가입된 이메일입니다."));
    }

    @DisplayName("회원가입시 중복된 username으로 가입하려는 경우 400 응답을 반환한다.")
    @Test
    void addCustomer_duplicated_username() {
        ExtractableResponse<Response> response = 회원가입_요청(
                new CustomerCreateRequest("philz@naver.com", "puterism", "12345678"));

        ErrorResponseWithField errorResponseWithField = response.as(ErrorResponseWithField.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponseWithField).usingRecursiveComparison()
                .isEqualTo(new ErrorResponseWithField("username", "이미 가입된 닉네임입니다."));
    }


    @DisplayName("회원 가입시 잘못된 형식의 email을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"not_email_format", "philz @gmail.com", ""})
    @NullSource
    void create_exception_parameter_email(String email) {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(email, "philz", "12345678");

        // when
        ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원 가입시 잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"01234567890", "", " "})
    @NullSource
    void create_exception_parameter_name(String username) {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("philz@gmail.com", username,
                "12345678");

        // when
        ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원 가입시 잘못된 형식의 password을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1234", "012345678901234567890"})
    @NullSource
    void create_exception_parameter_password(String password) {
        // given
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("philz@gmail.com", "philz", password);

        // when
        ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void findCustomer() {
        ExtractableResponse<Response> createResponse = 회원가입_요청(
                new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        long savedId = ID_추출(createResponse);

        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원조회_요청(token, savedId);

        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        CustomerResponse expected = new CustomerResponse(savedId, "roma@naver.com", "roma");

        assertAll(
                () -> assertThat(customerResponse).usingRecursiveComparison()
                        .isEqualTo(expected)
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void update() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("sojukang"));

        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        CustomerResponse expected = new CustomerResponse(savedId, "roma@naver.com", "sojukang");

        assertAll(
                () -> assertThat(customerResponse).usingRecursiveComparison()
                        .isEqualTo(expected)
        );
    }

    @DisplayName("내 정보 수정시 잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"01234567890", "", " "})
    @NullSource
    void update_exception_parameter_name(String username) {
        // given
        String token = 로그인_요청_및_토큰발급(new TokenRequest("puterism@naver.com", "12349053145"));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(username);

        // when
        ExtractableResponse<Response> response = 회원정보수정_요청(token, 1L, customerUpdateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void delete() {
        String token = 로그인_요청_및_토큰발급(new TokenRequest("puterism@naver.com", "12349053145"));
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 1L);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public String 로그인_요청_및_토큰발급(TokenRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();

        TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }

    public ExtractableResponse<Response> 회원가입_요청(CustomerCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    public long 회원가입_요청_및_ID_추출(CustomerCreateRequest request) {
        return ID_추출(회원가입_요청(request));
    }

    public ExtractableResponse<Response> 회원조회_요청(String token, Long id) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 회원정보수정_요청(String token, long id, CustomerUpdateRequest request) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 회원탈퇴_요청(String token, long id) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    private long ID_추출(ExtractableResponse<Response> response) {
        String[] locations = response.header("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }
}
