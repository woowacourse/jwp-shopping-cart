package woowacourse.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
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
        ErrorResponse response = MARU.NoLoginAnd().getMyInformation();
        assertThat(response.getMessage()).isEqualTo("[ERROR] 인증이 되지 않은 유저입니다.");
    }

    @DisplayName("토큰을 헤더에 담아 이름 수정을 요청하면 204 no content륿 반환한다.")
    @Test
    void updateMyName() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.loginAnd().updateMyName(NAME);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰을 헤더에 담아 비밀번호 수정을 요청하면 204 no content를 반환한다.")
    @Test
    void updateMyPassword() {
        MARU.register();
        ExtractableResponse<Response> response = MARU.loginAnd().updateMyPassword(PASSWORD);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
