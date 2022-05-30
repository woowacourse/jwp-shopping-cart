package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.EmailDuplicationCheckResponse;
import woowacourse.auth.dto.ErrorResponse;
import woowacourse.auth.dto.MemberCreateRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일, 비밀번호, 닉네임으로 회원 가입에 성공하면 201를 응답한다.")
    @Test
    void signUp_Created() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );

        ExtractableResponse<Response> response = post("/api/members", memberCreateRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("규칙에 맞지 않는 정보로 회원 가입을 시도하면 400을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc,1q2w3e4r!,닉네임", "abc@woowahan.com,1q2w3e4r,닉네임", "abc@woowahan.com,1q2w3e4r!,잘못된닉네임"})
    void signUp_BadRequest(String email, String password, String nickname) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);

        ExtractableResponse<Response> response = post("/api/members", memberCreateRequest);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).contains("형식이 올바르지 않습니다.");
    }

    @DisplayName("이미 회원으로 등록된 이메일인지와 200을 응답한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, false", "abc@naver.com, true"})
    void checkDuplicatedEmail_OK(String email, boolean expected) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "abc@woowahan.com",
                "1q2w3e4r!",
                "닉네임"
        );
        post("/api/members", memberCreateRequest);

        ExtractableResponse<Response> response = get("/api/members?email=" + email);
        boolean success = response.as(EmailDuplicationCheckResponse.class)
                .isSuccess();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(success).isEqualTo(expected);
    }

    @DisplayName("잘못된 이메일 형식으로 중복 체크를 하려하면 400을 응답한다.")
    @Test
    void checkDuplicatedEmail_BadRequest() {
        String invalid = "abc";

        ExtractableResponse<Response> response = get("/api/members?email=" + invalid);
        String message = response.as(ErrorResponse.class)
                .getMessage();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(message).isEqualTo("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
