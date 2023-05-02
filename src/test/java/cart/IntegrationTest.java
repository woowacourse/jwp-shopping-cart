package cart;

import static io.restassured.RestAssured.given;

import cart.service.dto.ProductRequest;
import io.restassured.RestAssured;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTest {

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
    @DisplayName("상품 추가 테스트")
    void createProduct() {
        final ProductRequest productRequest = new ProductRequest("TEST",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 4000);

        given()
                .body(productRequest).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/product")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("상품 추가 테스트 - 값이 비어있는 경우")
    void createProductValueEmpty() {
        final String jsonStrNameEmpty = "{ \"price\":\"321321\", \"imageUrl\":\"apple.png\"}";

        given()
                .body(jsonStrNameEmpty)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/product")
                .then()
                .statusCode(Response.SC_BAD_REQUEST);
    }

    @Sql("/test-fixture.sql")
    @Test
    @DisplayName("상품 수정 테스트")
    void editProduct() {
        final ProductRequest productRequest = new ProductRequest(1L, "TEST787",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 4000);

        given()
                .body(productRequest).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/product/1")
                .then().log().all()
                .statusCode(200);
    }

    @Sql("/test-fixture.sql")
    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/product/2")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("유저가 유효하지 않는 입력을 하는 경우 테스트")
    void badRequest() {
        final String jsonStr = "{ \"name\":\"홍실\", \"price\":\"321321\", \"imageUrl\":\"aaa.exe\"}";

        given()
                .body(jsonStr)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/product")
                .then()
                .statusCode(400);
    }
}
