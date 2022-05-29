package woowacourse.auth.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.acceptance.AcceptanceTestFixture.post;

@DisplayName("회원가입 관련 기능")
public class SignupAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원가입에 성공한다.")
    void signup() {
        // given
        SignupRequest signupRequest = new SignupRequest("id", "nickname", "password", "address", new PhoneNumber("010", "1234", "5678"));

        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("location")).isEqualTo("/signin")
        );

    }

    @Test
    @DisplayName("중복된 아이디로 회원가입 요청 시 400 상태코드를 반환한다.")
    void signupWithDuplicated() {
        // given
        SignupRequest signupRequest = new SignupRequest("id", "nickname", "password", "address", new PhoneNumber("010", "1234", "5678"));
        post("/signup", signupRequest);
        // when
        final ExtractableResponse<Response> response = post("/signup", signupRequest);
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
