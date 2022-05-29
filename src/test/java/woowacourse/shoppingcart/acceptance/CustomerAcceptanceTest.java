package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "test";
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "1234567890";
    private static final String ADDRESS = "서울 강남구 테헤란로 411, 성담빌딩 13층 (선릉 캠퍼스)";
    private static final String PHONE_NUMBER = "010-0000-0000";

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        CustomerSaveRequest request = new CustomerSaveRequest(USERNAME, EMAIL, PASSWORD, ADDRESS, PHONE_NUMBER);
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();

        assertAll(() -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).contains("customers");
        });
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
