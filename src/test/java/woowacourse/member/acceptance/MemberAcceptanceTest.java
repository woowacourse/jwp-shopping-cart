package woowacourse.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.TMember.MARU;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("멤버 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("email, password, name을 입력해서 회원가입을 진행하면 201 Created를 반환한다.")
    @Test
    void register() {
        ExtractableResponse<Response> response = MARU.register();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
