package cart.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() throws URISyntaxException {
        RestAssured.port = port;
    }

    @DisplayName("GET / 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetHome() {
        given().log().all()
                .when()
                .get("/")
                .then().log().all()
                .contentType(ContentType.HTML)
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("GET /admin 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetToAdmin() {
        given().log().all()
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.HTML);
    }

}
