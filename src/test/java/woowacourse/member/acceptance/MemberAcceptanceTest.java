package woowacourse.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.UNAUTHORIZED_MESSAGE;
import static woowacourse.helper.fixture.TMember.MARU;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.dto.MemberResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("멤버 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("email, password, name을 입력해서 회원가입을 진행하면 201 Created를 반환한다.")
    @Test
    void register() {
        ExtractableResponse<Response> response = MARU.register();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("토큰을 헤더에 담아 정보 조회를 요청하면 200 OK와 id, email, name을 반환한다.")
    @Test
    void getMyInformation() {
        MARU.register();
        MemberResponse response = MARU.loginAnd().getMyInformation();
        assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getEmail()).isEqualTo(MARU.getEmail()),
                () -> assertThat(response.getName()).isEqualTo(MARU.getName())
        );
    }

    @DisplayName("토큰이 존재하지 않을 때 정보 조회를 요청하면 401 unauthorized와 에러 메시지를 반환한다.")
    @Test
    void getMyInformationNoLogin() {
        ErrorResponse response = MARU.noLoginAnd().getMyInformation();
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_MESSAGE);
    }

    @DisplayName("토큰을 헤더에 담아 이름 수정을 요청하면 200 ok륿 반환한다.")
    @Test
    void updateMyName() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.loginAnd().updateMyName(NAME);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("로그인 하지 않고 이름 수정을 요청하면 401 unauthorized와 에러 메시지를 반환한다.")
    @Test
    void updateMyNameNoLogin() {
        ErrorResponse response = MARU.noLoginAnd().updateName(NAME);
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_MESSAGE);
    }

    @DisplayName("토큰을 헤더에 담아 비밀번호 수정을 요청하면 200 ok를 반환한다.")
    @Test
    void updateMyPassword() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.loginAnd().updateMyPassword(PASSWORD);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("로그인 하지 않고 비밀번호 수정을 요청하면 401 unauthorized와 에러 메시지를 반환한다.")
    @Test
    void updateMyPasswordNoLogin() {
        ErrorResponse response = MARU.noLoginAnd().updateMyPassword(PASSWORD);
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_MESSAGE);
    }

    @DisplayName("토큰을 헤더에 담고삭제를 요청하면 204 no content를 반환한다.")
    @Test
    void deleteMember() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.loginAnd().deleteMember();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("로그인 하지 않고 회원 삭제를 요청하면 401 unauthorized와 에러 메시지를 반환한다.")
    @Test
    void deleteMemberNoLogin() {
        ErrorResponse response = MARU.noLoginAnd().deleteMember();
        assertThat(response.getMessage()).isEqualTo(UNAUTHORIZED_MESSAGE);
    }

    @DisplayName("회원가입 전 이메일 중복체크에 성공하면 200 ok를 반환한다.")
    @Test
    void checkDuplicateEmail() {
        ExtractableResponse<Response> response = MARU.validateDuplicateEmail();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("이메일이 존재할 경우 중복체크를 하면 400 bad request를 반환한다.")
    @Test
    void checkDuplicateEmailDuplicate() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.validateDuplicateEmail();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
