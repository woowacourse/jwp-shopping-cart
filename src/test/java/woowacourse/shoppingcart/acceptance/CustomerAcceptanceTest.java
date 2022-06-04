package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.global.utils.CustomFixture.ID_추출;
import static woowacourse.global.utils.CustomFixture.로그인_요청_및_토큰발급;
import static woowacourse.global.utils.CustomFixture.회원가입_요청;
import static woowacourse.global.utils.CustomFixture.회원가입_요청_및_ID_추출;
import static woowacourse.global.utils.CustomFixture.회원정보수정_요청;
import static woowacourse.global.utils.CustomFixture.회원조회_요청;
import static woowacourse.global.utils.CustomFixture.회원탈퇴_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.utils.AcceptanceTest;
import woowacourse.global.utils.CustomFixture;
import woowacourse.shoppingcart.dto.FieldErrorResponse;
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

        FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(fieldErrorResponse).usingRecursiveComparison()
                .isEqualTo(new FieldErrorResponse("email", "이미 가입된 이메일입니다."));
    }

    @DisplayName("회원가입시 중복된 username으로 가입하려는 경우 400 응답을 반환한다.")
    @Test
    void addCustomer_duplicated_username() {
        ExtractableResponse<Response> response = 회원가입_요청(
                new CustomerCreateRequest("philz@naver.com", "puterism", "12345678"));

        FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(fieldErrorResponse).usingRecursiveComparison()
                .isEqualTo(new FieldErrorResponse("username", "이미 가입된 닉네임입니다."));
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

    @DisplayName("다른 사람의 정보 조회를 하면 403을 반환한다")
    @Test
    void findCustomer_otherId() {
        // given
        ExtractableResponse<Response> createResponse = 회원가입_요청(
                new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));
        long savedId = ID_추출(createResponse);
        long 다른사람의_ID = savedId + 1L;
        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));

        // when
        ExtractableResponse<Response> response = 회원조회_요청(token, 다른사람의_ID);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
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

    @DisplayName("내 정보 수정시 기존과 같은 username을 입력한 경우 200 OK를 응답한다.")
    @Test
    void update_same_origin_name() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("roma"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("내 정보 수정 시 중복된 username으로 변경하려는 경우 400 응답을 반환한다.")
    @Test
    void update_duplicated_username() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("yujo11"));

        FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(fieldErrorResponse).usingRecursiveComparison()
                        .isEqualTo(new FieldErrorResponse("username", "이미 가입된 닉네임입니다."))
        );
    }

    @DisplayName("내 정보 수정시 잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"01234567890", "", " "})
    @NullSource
    void update_exception_parameter_username(String username) {
        // given
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(username);

        // when
        ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, customerUpdateRequest);

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
        String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));

        // when
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("philz");
        ExtractableResponse<Response> response = 회원정보수정_요청(token, 다른사람의_ID, updateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void delete() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, savedId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("다른 사람의 정보를 수정하면 403을 반환한다")
    @Test
    void delete_otherId() {
        long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
        long 다른사람의_ID = savedId + 50;
        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 다른사람의_ID);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
