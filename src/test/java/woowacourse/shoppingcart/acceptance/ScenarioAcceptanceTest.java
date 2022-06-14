package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.CartFixture.장바구니_상품_추가_요청;
import static woowacourse.fixture.CustomerFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.CustomerFixture.회원탈퇴_요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.global.AcceptanceTest;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;

public class ScenarioAcceptanceTest extends AcceptanceTest {

    long 저장된_고객의_ID = 1L;
    long DB에_저장된_상품의_ID = 1;

    @DisplayName("가입 후 장바구니를 추가한 다음에 회원을 삭제할 수 있다")
    @Test
    void scenario_1() {
        String token = 로그인_요청_및_토큰발급(TokenRequest.from("puterism@naver.com", "12349053145"));
        장바구니_상품_추가_요청(token, 저장된_고객의_ID, DB에_저장된_상품의_ID, 4);

        ExtractableResponse<Response> response = 회원탈퇴_요청(token, 저장된_고객의_ID, CustomerDeleteRequest.from("12349053145"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("한 번 담긴 장바구니는 다시 재 등록할 수 없다")
    @Test
    void scenario_2() {
        String token = 로그인_요청_및_토큰발급(TokenRequest.from("puterism@naver.com", "12349053145"));
        장바구니_상품_추가_요청(token, 저장된_고객의_ID, DB에_저장된_상품의_ID, 4);
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, 저장된_고객의_ID, DB에_저장된_상품의_ID, 4);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("이미 담겨있는 상품입니다.")
        );
    }
}
