package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginTokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.ErrorResponseWithField;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

public class CustomerControllerTest extends AcceptanceTest {

    @DisplayName("회원가입시 중복된 email로 가입하려는 경우 400 응답을 반환한다.")
    @Test
    void addCustomer_duplicated_email() {
        ExtractableResponse<Response> response = 회원가입_요청(
            new CustomerCreateRequest("puterism@naver.com", "roma", "12345678"));

        ErrorResponseWithField errorResponseWithField = response.as(ErrorResponseWithField.class);

        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(errorResponseWithField).usingRecursiveComparison()
                .isEqualTo(new ErrorResponseWithField("email", "이미 가입된 이메일입니다."))
        );
    }

    @DisplayName("회원가입시 중복된 username으로 가입하려는 경우 400 응답을 반환한다.")
    @Test
    void addCustomer_duplicated_username() {
        ExtractableResponse<Response> response = 회원가입_요청(
            new CustomerCreateRequest("philz@naver.com", "puterism", "12345678"));

        ErrorResponseWithField errorResponseWithField = response.as(ErrorResponseWithField.class);

        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(errorResponseWithField).usingRecursiveComparison()
                .isEqualTo(new ErrorResponseWithField("username", "이미 가입된 닉네임입니다."))
        );
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

    @DisplayName("다른 사람의 정보 조회를 하면 403을 반환한다")
    @Test
    void findCustomer_otherId() {
        // given
        ExtractableResponse<Response> createResponse = 회원가입_요청(
            new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        long savedId = ID_추출(createResponse);
        long 다른사람의_ID = savedId + 1L;
        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));

        // when
        ExtractableResponse<Response> response = 회원조회_요청(token, 다른사람의_ID);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("내 정보 수정시 기존과 같은 username을 입력한 경우 200 OK를 응답한다.")
    @Test
    void update_same_origin_name() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("roma"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("내 정보 수정 시 중복된 username으로 변경하려는 경우 400 응답을 반환한다.")
    @Test
    void update_duplicated_username() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("yujo11"));

        ErrorResponseWithField errorResponseWithField = response.as(ErrorResponseWithField.class);

        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
            () -> assertThat(errorResponseWithField).usingRecursiveComparison()
                .isEqualTo(new ErrorResponseWithField("username", "이미 가입된 닉네임입니다."))
        );
    }

    @DisplayName("내 정보 수정시 잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"01234567890", "", " "})
    @NullSource
    void update_exception_parameter_name(String username) {
        // given
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(username);

        // when
        ExtractableResponse<Response> response = 회원정보수정_요청(token, 1L, customerUpdateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("다른 사람의 정보를 수정하면 403을 반환한다")
    @Test
    void update_otherId() {
        // given
        ExtractableResponse<Response> createResponse = 회원가입_요청(
            new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        long savedId = ID_추출(createResponse);
        long 다른사람의_ID = savedId + 1L;
        String token = 로그인_요청_및_토큰발급(new LoginRequest("roma@naver.com", "12345678"));

        // when
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("philz");
        ExtractableResponse<Response> response = 회원정보수정_요청(token, 다른사람의_ID, updateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("다른 사람의 정보를 삭제하면 403을 반환한다")
    @Test
    void delete_otherId() {
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));
        long 다른사람의_ID = 2L;
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 다른사람의_ID, "12349053145");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("잘못된 비밀번호로 회원 탈퇴를 요청하면 400을 반환한다")
    @Test
    void delete_invalidPassword() {
        String token = 로그인_요청_및_토큰발급(new LoginRequest("puterism@naver.com", "12349053145"));
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 1L, "98765432");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private String 로그인_요청_및_토큰발급(LoginRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/auth/login")
            .then().log().all()
            .extract();

        LoginTokenResponse loginTokenResponse = loginResponse.body().as(LoginTokenResponse.class);
        return loginTokenResponse.getAccessToken();
    }

    private ExtractableResponse<Response> 회원가입_요청(CustomerCreateRequest request) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers")
            .then().log().all()
            .extract();
    }

    private long 회원가입_요청_및_ID_추출(CustomerCreateRequest request) {
        return ID_추출(회원가입_요청(request));
    }

    private ExtractableResponse<Response> 회원조회_요청(String token, Long id) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .when().get("/api/customers/" + id)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원정보수정_요청(String token, long id, CustomerUpdateRequest request) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().put("/api/customers/" + id)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원탈퇴_요청(String token, long id, String password) {
        CustomerDeleteRequest request = new CustomerDeleteRequest(password);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers/{id}", id)
            .then().log().all()
            .extract();
    }

    private long ID_추출(ExtractableResponse<Response> response) {
        String[] locations = response.header("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }
}
