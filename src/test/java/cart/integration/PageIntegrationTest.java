package cart.integration;

import cart.BasicAuthorizationEncoder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private String getAuthenticationHeader() {
        final String email = "ditoo@wooteco.com";
        final String password = "ditoo1234";
        return BasicAuthorizationEncoder.encode(email, password);
    }

    @Test
    @DisplayName("메인 페이지 접속 성공")
    void mainPage_success() {
        RestAssured
                .given()
                .when().get("")
                .then().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @Test
    @DisplayName("장바구니 페이지 접근 성공")
    void cartPage_success() {
        RestAssured
                .given().header("Authorization", getAuthenticationHeader())
                .when().get("/cart")
                .then().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @Test
    @DisplayName("설정 페이지 접근 성공")
    void settingsPage_success() {
        RestAssured
                .given()
                .when().get("/settings")
                .then().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @Test
    @DisplayName("관리자 페이지 접속 성공")
    void adminPage_success() {
        RestAssured
                .given()
                .when().get("/admin")
                .then().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }
}
