package woowacourse.shoppingcart.acceptance;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import Fixture.SimpleRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.customer.request.EmailDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.request.UsernameDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.response.CustomerResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = SimpleRestAssured.saveCustomer(MAT_SAVE_REQUEST);

        assertAll(() -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).contains("customers");
        });
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMe() {
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);

        CustomerResponse customerResponse = SimpleRestAssured.findCustomer(accessToken);

        assertAll(() -> {
            assertThat(customerResponse.getId()).isNotNull();
            assertThat(customerResponse.getUsername()).isEqualTo(YAHO_USERNAME);
            assertThat(customerResponse.getEmail()).isEqualTo(YAHO_EMAIL);
            assertThat(customerResponse.getAddress()).isEqualTo(YAHO_ADDRESS);
            assertThat(customerResponse.getPhoneNumber()).isEqualTo(YAHO_PHONE_NUMBER);
        });
    }

    @DisplayName("유효하지 않는 토큰으로 내 정보 조회 시 401 상태코드를 반환한다.")
    @Test
    void getMe_error_invalidToken() {
        String accessToken = "aaaaaaa.bbbbbbb.ccccccc";

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("내 정보를 수정한다.")
    @Test
    void updateMe() {
        SimpleRestAssured.saveCustomer(MAT_SAVE_REQUEST);
        String accessToken = SimpleRestAssured.getAccessToken(MAT_TOKEN_REQUEST);

        SimpleRestAssured.updateCustomer(accessToken);

        CustomerResponse customerResponse = SimpleRestAssured.findCustomer(accessToken);
        assertAll(() -> {
            assertThat(customerResponse.getId()).isNotNull();
            assertThat(customerResponse.getUsername()).isEqualTo(MAT_USERNAME);
            assertThat(customerResponse.getEmail()).isEqualTo(MAT_EMAIL);
            assertThat(customerResponse.getAddress()).isEqualTo(UPDATE_ADDRESS);
            assertThat(customerResponse.getPhoneNumber()).isEqualTo(UPDATE_PHONE_NUMBER);
        });
    }

    @DisplayName("회원을 탈퇴한다.")
    @Test
    void deleteMe() {
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);
        String accessToken = SimpleRestAssured.getAccessToken(YAHO_TOKEN_REQUEST);

        SimpleRestAssured.deleteCustomer(accessToken);

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();
    }

    @DisplayName("회원 username 중복 검증을 한다.")
    @Test
    void checkDuplicateUsername() {
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UsernameDuplicateRequest(YAHO_USERNAME))
                .when().post("/api/customers/duplication/username")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        boolean duplicated = response.jsonPath().getObject("duplicated", Boolean.class);
        assertThat(duplicated).isEqualTo(true);
    }

    @DisplayName("회원 email 중복 검증을 한다.")
    @Test
    void checkDuplicateEmail() {
        SimpleRestAssured.saveCustomer(YAHO_SAVE_REQUEST);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new EmailDuplicateRequest(YAHO_EMAIL))
                .when().post("/api/customers/duplication/email")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        boolean duplicated = response.jsonPath().getObject("duplicated", Boolean.class);
        assertThat(duplicated).isEqualTo(true);
    }
}
