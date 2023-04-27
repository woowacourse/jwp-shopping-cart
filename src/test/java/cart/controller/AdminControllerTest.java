package cart.controller;

import static io.restassured.RestAssured.given;

import cart.controller.dto.SaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private AdminController adminController;

    @DisplayName("GET /admin 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetToAdmin() {
        given().log().all()
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @DisplayName("POST /admin/product 요청 시 Status Created 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusCreatedWhenRequestPostToAdminProduct() {
        given()
                .contentType(ContentType.JSON)
                .body(new SaveRequest("사과", 100, "domain.com")).log().all()
                .when()
                .post("/admin/product")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.HTML);
    }

    @DisplayName("PUT /admin/product/{id} 요청 시 Status Created 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusCreatedWhenRequestPutToAdminProductId() {
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new SaveRequest("사과", 100, "domain.com"))
                .when()
                .put("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.HTML);
    }

    @DisplayName("DELETE /admin/product/{id} 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestDeleteToAdminProductId() {
        given().log().all()
                .when()
                .delete("/admin/product/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }
}
