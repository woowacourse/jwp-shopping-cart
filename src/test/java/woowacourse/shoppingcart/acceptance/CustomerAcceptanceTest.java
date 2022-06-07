package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private final CustomerRequest customer = new CustomerRequest(
            "email", "Pw123456!", "name", "010-1234-5678", "address");
    private final LoginRequest loginRequest = new LoginRequest("email", "Pw123456!");

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // when
        ValidatableResponse response = requestHttpPost("", customer, "/customers");

        // then
        response.statusCode(HttpStatus.CREATED.value());
        response.body(
                "id", equalTo(1),
                "email", equalTo("email"),
                "name", equalTo("name"),
                "phone", equalTo("010-1234-5678"),
                "address", equalTo("address"));
    }

    @DisplayName("이메일 중복 여부 조회")
    @Test
    void emailDuplication() {
        // given
        CustomerRequest customer = new CustomerRequest(
                "email@naver.com", "Pw123456!", "name", "010-1234-5678", "address");
        requestHttpPost("", customer, "/customers");

        // when
        ValidatableResponse response = requestHttpGet("", "/customers/email?email=email@naver.com");

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value());
        response.body(containsString("중복된 email 입니다."));
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        requestHttpPost("", customer, "/customers");

        String accessToken = requestHttpPost("", loginRequest, "/auth/login")
                .extract().as(TokenResponse.class).getAccessToken();

        //when
        CustomerRequest newCustomer = new CustomerRequest(
                "email", "Pw123456!", "judy", "010-1111-2222", "address2");
        requestHttpPut(accessToken, newCustomer, "/customers");

        //then
        ValidatableResponse response = requestHttpGet(accessToken, "/customers");
        response.statusCode(HttpStatus.OK.value());
        response.body(
                "id", equalTo(1),
                "email", equalTo("email"),
                "name", equalTo("judy"),
                "phone", equalTo("010-1111-2222"),
                "address", equalTo("address2"));
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        requestHttpPost("", customer, "/customers");

        String accessToken = requestHttpPost("", loginRequest, "/auth/login")
                .extract().as(TokenResponse.class).getAccessToken();

        //when
        requestHttpDelete(accessToken, "/customers").statusCode(HttpStatus.NO_CONTENT.value());

        //then
        ValidatableResponse response = requestHttpGet(accessToken, "/customers");
        response.statusCode(HttpStatus.BAD_REQUEST.value());
        response.body(containsString("존재하지 않는 유저입니다."));
    }
}
