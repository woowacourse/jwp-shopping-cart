package cart.controller;

import static io.restassured.RestAssured.given;

import cart.entity.Product;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("관리자 도구 페이지 접근 테스트")
    void productList() {
        given()
                .when()
                .get("/admin")
                .then()
                .statusCode(200);
    }

    @Test
    void createProduct() {
        final Product product = new Product("TEST",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 4000);

        given()
                .body(product).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/admin/create")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/admin/1");
    }

    @Test
    @Sql({"/test-fixture.sql"})
    void editProduct() {
        final Product product = new Product(1L, "TEST787",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 4000);

        given()
                .body(product).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch("/admin/edit")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @Sql({"/test-fixture.sql"})
    void deleteProduct() {
        given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/delete/1")
                .then().log().all()
                .statusCode(200);
    }
}
