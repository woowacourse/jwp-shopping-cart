package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("진입 페이지를 생성한다.")
    void indexPage() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("관리자 페이지를 생성한다.")
    void adminPage() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("세팅 페이지를 생성한다.")
    void settingPage() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when()
                .get("/settings")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
