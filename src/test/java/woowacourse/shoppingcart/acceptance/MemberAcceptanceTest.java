package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.AcceptanceTestUtil.로그인_없이_회원_정보를_조회한다;
import static woowacourse.util.AcceptanceTestUtil.로그인을_하고_토큰을_받는다;
import static woowacourse.util.AcceptanceTestUtil.로그인을_한다;
import static woowacourse.util.AcceptanceTestUtil.비밀번호를_수정한다;
import static woowacourse.util.AcceptanceTestUtil.회원_정보를_수정한다;
import static woowacourse.util.AcceptanceTestUtil.회원_정보를_조회한다;
import static woowacourse.util.AcceptanceTestUtil.회원가입을_한다;
import static woowacourse.util.AcceptanceTestUtil.회원을_삭제한다;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.ErrorResponse;
import woowacourse.shoppingcart.dto.response.MemberResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 된 상태로 회원 정보를 조회하면 정보를 반환한다.")
    @Test
    void showLoginMember() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_하고_토큰을_받는다("abc@woowahan.com", "1q2w3e4r!");

        ExtractableResponse<Response> response = 회원_정보를_조회한다(token);
        MemberResponse responseBody = response.as(MemberResponse.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseBody).usingRecursiveComparison()
                .isEqualTo(new MemberResponse("abc@woowahan.com", "닉네임"));
    }

    @DisplayName("로그인하지 않고는 회원 정보에 접근할 수 없다.")
    @Test
    void showLoginMember_WithoutLogin() {
        ExtractableResponse<Response> response = 로그인_없이_회원_정보를_조회한다();
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(message).isEqualTo("로그인이 필요합니다.");
    }

    @DisplayName("로그인된 상태로 회원 정보를 수정하면 회원의 정보가 요청대로 바뀐다.")
    @Test
    void updateMember() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_하고_토큰을_받는다("abc@woowahan.com", "1q2w3e4r!");
        MemberUpdateRequest requestBody = new MemberUpdateRequest("바뀐닉네임");

        ExtractableResponse<Response> response = 회원_정보를_수정한다(token, requestBody);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> responseAfterUpdate = 회원_정보를_조회한다(token);
        String updatedNickname = responseAfterUpdate.as(MemberResponse.class)
                        .getNickname();

        assertThat(responseAfterUpdate.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(updatedNickname).isEqualTo("바뀐닉네임");
    }

    @DisplayName("로그인된 상태로 비밀번호를 수정하면 요청대로 바뀌고 바뀐 비밀번호로 로그인할 수 있다.")
    @Test
    void updatePassword_LoginWithUpdatedPassword() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_하고_토큰을_받는다("abc@woowahan.com", "1q2w3e4r!");
        PasswordUpdateRequest requestBody = new PasswordUpdateRequest("1q2w3e4r@");

        ExtractableResponse<Response> response = 비밀번호를_수정한다(token, requestBody);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> loginResponse = 로그인을_한다("abc@woowahan.com", "1q2w3e4r@");
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("로그인된 상태로 회원 삭제를 요청하면 회원 정보가 삭제되어 로그인할 수 없다.")
    @Test
    void deleteMember_CannotLogin() {
        회원가입을_한다("abc@woowahan.com", "1q2w3e4r!", "닉네임");
        String token = 로그인을_하고_토큰을_받는다("abc@woowahan.com", "1q2w3e4r!");

        ExtractableResponse<Response> response = 회원을_삭제한다(token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> loginResponse = 로그인을_한다("abc@woowahan.com", "1q2w3e4r!");
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
