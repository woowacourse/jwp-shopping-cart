package woowacourse.shoppingcart.acceptance;

import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.http.HttpStatus;
import woowacourse.acceptance.RestAssuredFixture;
import woowacourse.acceptance.AcceptanceTest;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void addCustomer() {
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "123456q!q!");

        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value())
                .body("username", is("alien"))
                .body("email", is("alien@woowa.com"));
    }

    @Test
    @DisplayName("회원가입을 할 수 없다 - 중복된 이름 입력")
    void addCustomerDuplicateUsernameException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "123456q!");

        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value())
                .body("username", is("alien"))
                .body("email", is("alien@woowa.com"));

        //when & then
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("회원가입을 할 수 없다 - 중복된 이메일 입력")
    void addCustomerDuplicateEmailException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("alien", "alien@woowa.com", "123456q!");

        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value())
                .body("username", is("alien"))
                .body("email", is("alien@woowa.com"));

        //when & then
        SignUpRequest signUpRequest2 = new SignUpRequest("rennon", "alien@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest2, "users", HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("회원가입을 할 수 없다 - username null 검증")
    void addCustomerWrongUsernameException(String username) {
        //given
        SignUpRequest signUpRequest = new SignUpRequest(username, "alien@woowa.com", "123456q!");

        //when & then
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @DisplayName("회원가입을 할 수 없다 - 유저 이름이 32자를 초과하는 경우")
    @CsvSource(value = {"22", "23"})
    void addCustomerMaxSizeUsernameException(int number) {
        //given
        String username = IntStream.range(0, number)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        SignUpRequest signUpRequest = new SignUpRequest(username, "alien@woowa.com", "123456q!");

        //when & then
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @DisplayName("회원 가입을 할 수 없다. - email이 64자를 초과하는 경우")
    @CsvSource(value = {"33", "34"})
    void signUpEmailSizeException(int number) {
        //given
        String email = IntStream.range(0, number)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        SignUpRequest signUpRequest = new SignUpRequest("rennon", email + "@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("회원 가입을 할 수 없다. - password에 한글이 포함된 경우")
    void signUpWrongPasswordFormatException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123그린456");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("회원 가입을 할 수 없다. - password null 검증")
    void signUpPasswordNullException(String password) {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", password);
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("회원 가입을 할 수 없다. - password가 6자 미만인 경우")
    void signUpPasswordSizeException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "12345");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("로그인을 할 수 있다.")
    void signInCustomer() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        RestAssuredFixture.get(token, "/users/me", HttpStatus.OK.value())
                .body("username", is("rennon"))
                .body("email", is("rennon@woowa.com"));
    }

    @Test
    @DisplayName("로그인을 할 수 없다. - 등록되지 않은 email")
    void signUnauthorizedEmailException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        LogInRequest logInRequest = new LogInRequest("rennon1@woowa.com", "123456q!");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("로그인을 할 수 없다. - 등록된 정보와 다른 password")
    void signInUnauthorizedPasswordException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123578q!");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.UNAUTHORIZED.value());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("로그인 할 수 없다 - email null 검증")
    void signInBlankEmailException(String email) {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        LogInRequest logInRequest = new LogInRequest(email, "123456q!");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("로그인을 할 수 없다. - email에 한글을 입력한 경우")
    void signInEmailException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        LogInRequest logInRequest = new LogInRequest("레넌@woowa.com", "123456q!");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("로그인을 할 수 없다. - email 형식이 맞지 않는 경우")
    void signInWrongFormatEmailException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        LogInRequest logInRequest = new LogInRequest("rennonwoowa.com", "123456q!");
        RestAssuredFixture.post(logInRequest, "/login", HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        RestAssuredFixture.get(token, "/users/me", HttpStatus.OK.value())
                .body("username", is("rennon"))
                .body("email", is("rennon@woowa.com"));
    }

    @DisplayName("토큰이 없으면 내 정보를 조회할 수 없다.")
    @Test
    void getMeException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.get("dummy", "/users/me", HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("123456q!", "125678");
        RestAssuredFixture.patch(updatePasswordRequest, token, "/users/me", HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 내 정보를 수정할 수 없다")
    @Test
    void updateMeThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest("123456q!", "125678");
        RestAssuredFixture.patch(updatePasswordRequest, "", "/users/me", HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("회원 탈퇴")
    @Test
    void deleteMe() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456q!");
        RestAssuredFixture.delete(deleteCustomerRequest, token, "/users/me", HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 탈퇴 - 비밀 번호가 틀린 경우 예외")
    @Test
    void deleteInValidPasswordException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        String token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123567");
        RestAssuredFixture.delete(deleteCustomerRequest, token, "/users/me", HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("토큰이 없으면 탈퇴할 수 없다")
    @Test
    void deleteMeThrowException() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "users", HttpStatus.CREATED.value());

        //when & then
        DeleteCustomerRequest deleteCustomerRequest = new DeleteCustomerRequest("123456q!");
        RestAssuredFixture.delete(deleteCustomerRequest, "", "/users/me", HttpStatus.UNAUTHORIZED.value());
    }
}
