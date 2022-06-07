package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.AuthFixture.login;
import static woowacourse.fixture.CustomerFixture.findById;
import static woowacourse.fixture.CustomerFixture.update;
import static woowacourse.fixture.CustomerFixture.updatePassword;
import static woowacourse.fixture.CustomerFixture.withdraw;
import static woowacourse.fixture.CustomerFixture.signUp;
import static woowacourse.fixture.CustomerFixture.matchPassword;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

@DisplayName("회원 관련 기능 인수테스트")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUpCustomer() {
        ExtractableResponse<Response> response = signUp("test@woowacourse.com", "test", "1234asdf!");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("중복된 아이디로 회원가입을 하면 400 에러가 발생한다.")
    @Test
    void signUpDuplicateUserId() {
        ExtractableResponse<Response> response = signUp(
                "puterism@woowacourse.com", "test", "1234asdf!"
        );

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 아이디입니다.")
        );
    }

    @DisplayName("중복된 닉네임으로 회원가입을 하면 400 에러가 발생한다.")
    @Test
    void signUpDuplicateNickname() {
        ExtractableResponse<Response> response = signUp(
                "test@woowacourse.com", "nickname", "1234asdf!"
        );

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 닉네임입니다.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ddfkdksfk", "ddddddddddnaver.com", "cdd@dd"})
    @DisplayName("잘못된 아이디 형식으로 회원가입을 하면 400 에러가 발생한다.")
    void signUpInvalidFormatUserId(final String userId) {
        ExtractableResponse<Response> response = signUp(
                userId, "nickname", "1234asdf!"
        );

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("아이디는 이메일 형식으로 입력해주세요.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@#$%", "a", "aaaaaaaaaaa"})
    @DisplayName("잘못된 닉네임 형식으로 회원가입을 하면 400 에러가 발생한다.")
    void signUpInvalidFormatNickname(final String nickname) {
        ExtractableResponse<Response> response = signUp(
                "test@woowacourse.com", nickname, "1234asdf!"
        );

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234!@#$", "1234asdf", "asdf!@#$", "a", "aaaaaaaaaaaaaaaaa"})
    @DisplayName("잘못된 비밀번호 형식으로 회원가입을 하면 400 에러가 발생한다.")
    void signUpInvalidFormatPassword(final String password) {
        ExtractableResponse<Response> response = signUp(
                "test@woowacourse.com", "test", password
        );

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.")
        );
    }

    @DisplayName("로그인을 한다.")
    @Test
    void loginCustomer() {
        ExtractableResponse<Response> response = login("puterism@woowacourse.com", "1234asdf!");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getString("userId")).isEqualTo("puterism@woowacourse.com"),
                () -> assertThat(response.body().jsonPath().getString("nickname")).isEqualTo("nickname")
        );
    }

    @DisplayName("존재하지 않은 회원 정보로 로그인하면 401 에러가 발생한다.")
    @Test
    void loginNotExistingCustomer() {
        ExtractableResponse<Response> response = login("test@woowacourse.com", "1234asdf!");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("틀린 비밀번호로 로그인하면 401 에러가 발생한다.")
    @Test
    void loginInvalidPassword() {
        ExtractableResponse<Response> response = login("puterism@woowacourse.com", "invalidPassword");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("내 정보 조회를 한다.")
    @Test
    void getMe() {
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        ExtractableResponse<Response> secondResponse = findById(token);

        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("userId")).isEqualTo(
                        "puterism@woowacourse.com"),
                () -> assertThat(secondResponse.body().jsonPath().getString("nickname")).isEqualTo("nickname")
        );
    }

    @DisplayName("탈퇴한 회원의 정보 조회를 하면 404 에러가 발생한다.")
    @Test
    void getMeWithdrawal() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        // when
        ExtractableResponse<Response> secondResponse = findById(token);

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("내 정보 수정을 한다.")
    @Test
    void updateMeNickname() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = update(token, "유콩", "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("존재하지 않는 사용자가 내 정보 수정을 요청하면 404 에러가 발생한다.")
    @Test
    void updateMeNicknameNotExistingCustomer() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        // when
        ExtractableResponse<Response> secondResponse = update(token, "유콩", "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("틀린 비밀번호로 내 정보 수정을 요청하면 402 에러가 발생한다.")
    @Test
    void updateMeNicknameInvalidPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = update(token, "유콩", "invalidPassword");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("이미 존재하는 닉네임으로 내 정보 수정을 요청하면 400 에러가 발생한다.")
    @Test
    void updateMeNicknameDuplicateNickname() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = update(token, "nickname", "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "이미 존재하는 닉네임입니다.")
        );
    }

    @DisplayName("내 비밀번호를 수정한다.")
    @Test
    void updateMePassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = updatePassword(token, "1234asdf!", "asdf1234!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("내 비밀번호 변경 요청 시 기존 비밀번호가 일치하지 않는 경우 400 에러가 발생한다.")
    @Test
    void updateMeInvalidPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = updatePassword(token, "asdf1234!", "asdf1234!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("내 비밀번호 변경 요청 시 수정하려는 비밀번호가 올바른 형식이 아닐 경우 400 에러가 발생한다.")
    @Test
    void updateMeInvalidPasswordFormat() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = updatePassword(token, "1234asdf!", "invalidPassword");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.")
        );
    }

    @DisplayName("회원탈퇴를 한다.")
    @Test
    void deleteMe() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = withdraw(token, "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @DisplayName("탈퇴한 사용자가 탈퇴를 요청할 경우 404 에러가 발생한다.")
    @Test
    void deleteMeWithdrawal() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");
        withdraw(token, "1234asdf!");

        // when
        ExtractableResponse<Response> secondResponse = withdraw(token, "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("틀린 비밀번호로 탈퇴할 경우 402 에러가 발생한다.")
    @Test
    void deleteMeInvalidPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = withdraw(token, "invalidPassword");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @Test
    void matchingCustomerPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = matchPassword(token, "1234asdf!");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("비밀번호가 일치하지 않은지 확인한다.")
    @Test
    void matchingCustomerInvalidPassword() {
        // given
        ExtractableResponse<Response> firstResponse = login("puterism@woowacourse.com", "1234asdf!");
        String token = firstResponse.body().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> secondResponse = matchPassword(token, "invalidPassword");

        // then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(secondResponse.body().jsonPath().getString("message")).isEqualTo(
                        "비밀번호가 일치하지 않습니다.")
        );
    }
}
