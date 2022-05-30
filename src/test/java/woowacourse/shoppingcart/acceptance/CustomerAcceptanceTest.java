package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원 가입에 성공하면 상태코드 201을 반환한다.")
    @Test
    void registerCustomer() {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");

        ExtractableResponse<Response> response = RequestHandler.postRequest("/customers", customerRegisterRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        assertAll(() -> {
            assertThat(response.header("Location")).isEqualTo("/customers/1");
            assertThat(response.jsonPath().getObject(".", CustomerResponse.class))
                    .extracting("email", "userName")
                    .containsExactly("guest@woowa.com", "guest");
        });
    }

    @DisplayName("동일한 이메일로 회원 가입 요청시 상태코드 400을 반환한다.")
    @Test
    void registerCustomerWithDuplicatedEmail() {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest", "qwe123!@#");
        RequestHandler.postRequest("/customers", customerRegisterRequest);

        CustomerRegisterRequest duplicatedCustomerRegisterRequest = new CustomerRegisterRequest(
                "guest@woowa.com", "guest1", "qwe123!@#");
        ExtractableResponse<Response> response = RequestHandler.postRequest("/customers", customerRegisterRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
