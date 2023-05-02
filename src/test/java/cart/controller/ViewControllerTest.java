package cart.controller;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static io.restassured.RestAssured.given;

@WebMvcTest(ViewControllerTest.class)
class ViewControllerTest {

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
