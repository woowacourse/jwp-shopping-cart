package cart.mvcconfig.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.CartAddRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthInterceptorTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/cart/** URI 요청 시 Authorization 헤더가 존재하지 않으면 예외가 발생한다.")
    void preHandle_throws_when_no_authorization_header() {
        Response response1 = RestAssured.given().log().all()
                .when()
                .get("/cart/products")
                .then()
                .extract().response();

        Response response2 = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartAddRequest(1L))
                .when()
                .post("/cart")
                .then()
                .extract().response();

        Response response3 = RestAssured.given().log().all()
                .when()
                .delete("/cart/1")
                .then()
                .extract().response();

        assertAll(
                () -> assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response1.getBody().asString()).isEqualTo("인증 관련 오류가 발생했습니다. 관리자에게 문의하세요."),
                () -> assertThat(response2.getBody().asString()).isEqualTo("인증 관련 오류가 발생했습니다. 관리자에게 문의하세요."),
                () -> assertThat(response3.getBody().asString()).isEqualTo("인증 관련 오류가 발생했습니다. 관리자에게 문의하세요.")
        );
    }

    @Test
    @DisplayName("/cart/** URI 요청 시 사용자 인증 정보에 해당하는 사용자가 없으면 예외가 발생한다.")
    void preHandle_throws_when_not_found_member() {
        Response response1 = RestAssured.given().log().all()
                .auth().preemptive().basic("test", "test")
                .when()
                .get("/cart/products")
                .then()
                .extract().response();

        Response response2 = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartAddRequest(1L))
                .auth().preemptive().basic("test", "test")
                .when()
                .post("/cart")
                .then()
                .extract().response();

        Response response3 = RestAssured.given().log().all()
                .auth().preemptive().basic("test", "test")
                .when()
                .delete("/cart/1")
                .then()
                .extract().response();

        assertAll(
                () -> assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response1.getBody().asString()).isEqualTo("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다."),
                () -> assertThat(response2.getBody().asString()).isEqualTo("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다."),
                () -> assertThat(response3.getBody().asString()).isEqualTo("사용자 인증 정보에 해당하는 사용자가 존재하지 않습니다.")
        );
    }
}
