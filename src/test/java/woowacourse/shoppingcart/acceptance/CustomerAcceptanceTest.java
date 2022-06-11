package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.RestAssuredFixture.postLogin;
import static woowacourse.fixture.shoppingcart.TCustomer.ROOKIE;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.global.exception.ErrorResponse;
import woowacourse.shoppingcart.application.dto.CustomerResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원이 이메일, 비밀번호, 닉네임을 입력해서 회원가입을 하면 상태코드 200 Ok를 반환한다.")
    void saveCustomer() {
        ExtractableResponse<Response> response = ROOKIE.signUp();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("회원이 이메일, 비밀번호, 기존에 등록된 닉네임을 입력해서 회원가입을 하면 상태코드 400 bad request와 에러 메시지를 반환한다.")
    void failedSaveCustomer() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse errorResponse = ROOKIE.signUpDuplicatedOfNickname();

        // then
        assertThat(errorResponse.getMessage()).isEqualTo("[ERROR] 이미 존재하는 닉네임입니다.");
    }

    @Test
    @DisplayName("헤더에 토큰을 담아 회원 정보 조회를 요청하면 id, email, nickname을 반환하고 상태코드 200 Ok를 반환한다.")
    void getMyInfo() {
        // given
        ROOKIE.signUp();

        // when
        CustomerResponse response = ROOKIE.signInAnd().showMyInfo();

        // then
        assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getEmail()).isEqualTo(ROOKIE.getEmail()),
                () -> assertThat(response.getNickname()).isEqualTo(ROOKIE.getNickname()));
    }

    @Test
    @DisplayName("헤더에 토큰을 보내지 않고 회원 정보 조회를 요청하면 상태코드 400 bad request와 에러 메시지를 반환한다.")
    void failedGetMyInfo() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse response = ROOKIE.noSignInAnd().showMyInfo();

        // then
        assertThat(response.getMessage()).isEqualTo("토큰이 존재하지 않습니다.");
    }


    @Test
    @DisplayName("헤더에 토큰을 담아 회원 정보 수정을 요청하면 상태코드 204 no content를 반환한다.")
    void updateNickname() {
        // given
        ROOKIE.signUp();

        // when
        ExtractableResponse<Response> response = ROOKIE.signInAnd().changeNickname("newRookie");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("헤더에 토큰을 담아 비밀번호 수정을 요청하면 상태코드 204 no content를 반환한다.")
    void updatePassword() {
        // given
        ROOKIE.signUp();

        // when
        ExtractableResponse<Response> response = ROOKIE.signInAnd().changePassword(ROOKIE.getPassword(), "qweR123!@");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("헤더에 토큰을 보내지 않고 정보 수정을 요청하면 상태코드 400 bad request와 에러 메시지를 반환한다.")
    void failedUpdateCustomerOrPassword() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse responseNickname = ROOKIE.noSignInAnd().changeNickname("newRookie");
        ErrorResponse responsePassword = ROOKIE.noSignInAnd().changePassword(ROOKIE.getPassword(), "qweR123!@");

        // then
        assertAll(
                () -> assertThat(responseNickname.getMessage()).isEqualTo("토큰이 존재하지 않습니다."),
                () -> assertThat(responsePassword.getMessage()).isEqualTo("토큰이 존재하지 않습니다.")
        );
    }

    @Test
    @DisplayName("헤더에 토큰을 담아 회원 정보 삭제를 요청하면 상태코드 204 no content를 반환한다.")
    void deleteCustomer() {
        // given
        ROOKIE.signUp();

        // when
        ExtractableResponse<Response> response = ROOKIE.signInAnd().deleteCustomer();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("헤더에 토큰을 보내지 않고 회원 정보 삭제를 요청하면 400 bad request와 에러 메시지를 반환한다.")
    void failedDeleteCustomer() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse response = ROOKIE.noSignInAnd().deleteCustomer();

        // then
        assertThat(response.getMessage()).isEqualTo("토큰이 존재하지 않습니다.");
    }

    public static TokenResponse 로그인_되어_있음(String email, String password) {
        ExtractableResponse<Response> response = postLogin(email, password);
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        return tokenResponse;
    }
}
