package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.AuthFixture.로그인_요청;
import static woowacourse.fixture.CustomerFixture.ID_추출;
import static woowacourse.fixture.CustomerFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomerFixture.회원가입_요청;
import static woowacourse.fixture.CustomerFixture.회원가입_요청_및_ID_추출;
import static woowacourse.fixture.CustomerFixture.회원정보수정_요청;
import static woowacourse.fixture.CustomerFixture.회원조회_요청;
import static woowacourse.fixture.CustomerFixture.회원탈퇴_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.FieldErrorResponse;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

@DisplayName("회원 E2E")

public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 할 때")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SignUp {

        @DisplayName("성공하면 200을 반환한다")
        @Test
        void addCustomer() {
            ExtractableResponse<Response> response = 회원가입_요청(
                    new CustomerCreateRequest("philz@gmail.com", "swcho", "1q2w3e4r!"));
            long savedId = ID_추출(response);

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                    () -> assertThat(response.header("Location")).isEqualTo("/api/customers/" + savedId)
            );
        }

        @DisplayName("중복된 email로 가입하려는 경우 400을 반환한다.")
        @Test
        void addCustomer_duplicated_email() {
            ExtractableResponse<Response> response = 회원가입_요청(
                    new CustomerCreateRequest("puterism@naver.com", "roma", "12345678"));

            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(fieldErrorResponse).usingRecursiveComparison()
                    .isEqualTo(new FieldErrorResponse("email", "이미 가입된 이메일입니다."));
        }

        @DisplayName("중복된 username으로 가입하려는 경우 400을 반환한다.")
        @Test
        void addCustomer_duplicated_username() {
            ExtractableResponse<Response> response = 회원가입_요청(
                    new CustomerCreateRequest("philz@naver.com", "puterism", "12345678"));

            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(fieldErrorResponse).usingRecursiveComparison()
                    .isEqualTo(new FieldErrorResponse("username", "이미 가입된 닉네임입니다."));
        }

        @DisplayName("잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
        @ParameterizedTest(name = "\"{0}\" :  {1}")
        @MethodSource("invalid_usernames")
        void create_exception_parameter_name(String username, String errorMessage) {
            // given
            CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("philz@gmail.com", username,
                    "12345678");

            // when
            ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);
            FieldErrorResponse errorResponse = response.as(FieldErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(errorResponse.getMessage()).isEqualTo(errorMessage);
        }

        Stream<Arguments> invalid_usernames() {
            return Stream.of(
                    Arguments.of(null, "닉네임 1자 이상 10자 이하여야합니다."),
                    Arguments.of("", "닉네임 1자 이상 10자 이하여야합니다."),
                    Arguments.of(" ", "닉네임에는 공백이 들어가면 안됩니다."),
                    Arguments.of("a b", "닉네임에는 공백이 들어가면 안됩니다."),
                    Arguments.of("01234567890", "닉네임 1자 이상 10자 이하여야합니다.")
            );
        }

        // TODO 공백의 경우에 대해 랜덤하게 테스트 성공/실패가 나뉘는 현상이 있는데 알아낼 것 !
        @DisplayName("잘못된 형식의 email을 입력한 경우 400을 반환한다.")
        @ParameterizedTest(name = "\"{0}\" :  {1}")
        @MethodSource("invalid_emails")
        void create_exception_parameter_email(String email, String errorMessage) {
            // given
            CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(email, "philz", "12345678");

            // when
            ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);
            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(fieldErrorResponse.getMessage()).isEqualTo(errorMessage);
        }

        Stream<Arguments> invalid_emails() {
            return Stream.of(
                    Arguments.of(null, "이메일은 8자 이상 50자 이하여야합니다."),
                    Arguments.of("not_email_format", "이메일 형식을 지켜야합니다."),
                    Arguments.of("a @gmail.com", "이메일에는 공백이 들어가면 안됩니다."),
                    Arguments.of("a@b.kr", "이메일은 8자 이상 50자 이하여야합니다.")
            );
        }

        // TODO 공백의 경우에 대해 랜덤하게 테스트 성공/실패가 나뉘는 현상이 있는데 알아낼 것 !
        @DisplayName("잘못된 형식의 password을 입력한 경우 400 응답을 반환한다.")
        @ParameterizedTest(name = "\"{0}\" :  {1}")
        @MethodSource("invalid_passwords")
        void create_exception_parameter_password(String password, String errorMessage) {
            // given
            CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("philz@gmail.com", "philz",
                    password);

            // when
            ExtractableResponse<Response> response = 회원가입_요청(customerCreateRequest);
            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(fieldErrorResponse.getMessage()).isEqualTo(errorMessage);
        }

        Stream<Arguments> invalid_passwords() {
            return Stream.of(
                    Arguments.of(null, "비밀번호는 8자 이상 20자 이하여야합니다."),
                    Arguments.of("1234", "비밀번호는 8자 이상 20자 이하여야합니다."),
                    Arguments.of("012345678901234567890", "비밀번호는 8자 이상 20자 이하여야합니다."),
                    Arguments.of("1234 45678", "비밀번호에는 공백이 들어가면 안됩니다."),
                    Arguments.of(" ", "비밀번호는 8자 이상 20자 이하여야합니다.")
            );
        }
    }

    @DisplayName("내 정보를 조회할 때")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Find {

        @DisplayName("가입, 로그인 후 토큰을 통해서 정보를 조회할 수 있다")
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
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(customerResponse).usingRecursiveComparison().isEqualTo(expected)
            );
        }

        @DisplayName("가입, 로그인 후 비밀번호가 다른 경우 401을 반환한다.")
        @Test
        void fail_login_when_wrong_password() {
            회원가입_요청(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

            String 틀린_비밀번호 = "wrong_password";
            ExtractableResponse<Response> loginResponse = 로그인_요청(new TokenRequest("roma@naver.com", 틀린_비밀번호));
            ErrorResponse errorResponse = loginResponse.as(ErrorResponse.class);

            assertAll(
                    () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                    () -> assertThat(errorResponse.getMessage()).isEqualTo("id 또는 비밀번호가 틀렸습니다.")
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
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(errorResponse.getMessage()).isEqualTo("권한이 없는 요청입니다.");
        }
    }

    @DisplayName("내 정보를 수정할 때")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Update {

        @DisplayName("성공하면 200을 반환한다")
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

        @DisplayName("기존과 같은 username을 입력한 경우 200 OK를 응답한다.")
        @Test
        void update_same_origin_name() {
            long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("roma@naver.com", "roma", "12345678"));

            String token = 로그인_요청_및_토큰발급(new TokenRequest("roma@naver.com", "12345678"));
            ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, new CustomerUpdateRequest("roma"));
            CustomerResponse customerResponse = response.as(CustomerResponse.class);
            CustomerResponse expected = new CustomerResponse(savedId, "roma@naver.com", "roma");

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(customerResponse).isEqualTo(expected);
        }

        @DisplayName("중복된 username으로 변경하려는 경우 400 응답을 반환한다.")
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

        @DisplayName("잘못된 형식의 username을 입력한 경우 400 응답을 반환한다.")
        @ParameterizedTest(name = "\"{0}\" :  {1}")
        @MethodSource("invalid_usernames")
        void update_exception_parameter_username(String username, String errorMessage) {
            // given
            long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
            String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
            CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(username);

            // when
            ExtractableResponse<Response> response = 회원정보수정_요청(token, savedId, customerUpdateRequest);
            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(fieldErrorResponse.getMessage()).isEqualTo(errorMessage);
        }

        Stream<Arguments> invalid_usernames() {
            return Stream.of(
                    Arguments.of(null, "닉네임 1자 이상 10자 이하여야합니다."),
                    Arguments.of("", "닉네임 1자 이상 10자 이하여야합니다."),
                    Arguments.of(" ", "닉네임에는 공백이 들어가면 안됩니다."),
                    Arguments.of("a b", "닉네임에는 공백이 들어가면 안됩니다."),
                    Arguments.of("01234567890", "닉네임 1자 이상 10자 이하여야합니다.")
            );
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
            FieldErrorResponse fieldErrorResponse = response.as(FieldErrorResponse.class);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(fieldErrorResponse.getMessage()).isEqualTo("권한이 없는 요청입니다.");
        }
    }

    @DisplayName("내 계정을 삭제할 때")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Delete {

        @DisplayName("정상적인 요청이라면 200을 반환한다.")
        @Test
        void delete() {
            long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
            String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
            CustomerDeleteRequest requestBody = new CustomerDeleteRequest("123456789");

            ExtractableResponse<Response> response = 회원탈퇴_요청(token, savedId, requestBody);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("비밀번호가 다른 경우 400을 반환한다.")
        @Test
        void delete_other_case() {
            long savedId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
            String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
            String 다른_비밀번호 = "other_password";
            CustomerDeleteRequest requestBody = new CustomerDeleteRequest(다른_비밀번호);

            ExtractableResponse<Response> response = 회원탈퇴_요청(token, savedId, requestBody);
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(errorResponse.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
        }

        @DisplayName("다른 사람의 정보를 삭제하면 403을 반환한다")
        @Test
        void delete_otherId() {
            회원가입_요청_및_ID_추출(new CustomerCreateRequest("philz@gmail.com", "swcho", "123456789"));
            String token = 로그인_요청_및_토큰발급(new TokenRequest("philz@gmail.com", "123456789"));
            long 다른사람의_ID = 1;
            String 다른사람의_올바른_비밀번호 = "12349053145";
            CustomerDeleteRequest requestBody = new CustomerDeleteRequest(다른사람의_올바른_비밀번호);

            ExtractableResponse<Response> response = 회원탈퇴_요청(token, 다른사람의_ID, requestBody);
            ErrorResponse errorResponse = response.as(ErrorResponse.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(errorResponse.getMessage()).isEqualTo("권한이 없는 요청입니다.");
        }
    }
}
