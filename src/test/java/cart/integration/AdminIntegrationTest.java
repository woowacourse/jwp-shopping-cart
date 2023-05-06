package cart.integration;

import static io.restassured.RestAssured.given;

import cart.service.dto.MemberRequest;
import cart.service.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminIntegrationTest {

    @LocalServerPort
    private int port;

    private String authorization;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        authorization = "Basic am91cm5leUBnbWFpbC5jb206Y0dGemMzZHZjbVE9";
    }

    @Test
    @DisplayName("상품 리스트를 조회한다.")
    @Sql("classpath:init.sql")
    void getProducts() {
        // given
        addAdminMember();

        // when, then
        given()
            .when()
            .header("Authorization", authorization)
            .get("/admin")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품을 추가한다.")
    @Sql("classpath:init.sql")
    void addProduct() {
        // given
        addAdminMember();
        final ProductRequest productRequest = new ProductRequest(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", authorization)
            .when()
            .body(productRequest)
            .post("/admin/register")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    @Sql("classpath:init.sql")
    void updateProduct() {
        // given
        addAdminMember();
        final ProductRequest productRequest = new ProductRequest(1L, "치킨", "chickenUrl", 20000, "KOREAN");
        addSampleProduct();

        // when, then
        given()
            .header("Authorization", authorization)
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .put("/admin/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    @Sql("classpath:init.sql")
    void deleteProduct() {
        // given
        addAdminMember();
        addSampleProduct();

        // when, then
        given()
            .header("Authorization", authorization)
            .when()
            .delete("/admin/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void addAdminMember() {
        final MemberRequest journey = new MemberRequest(1L, "ADMIN", "journey@gmail.com",
            "password", "져니", "010-1234-5678");

        given()
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(journey)
            .post("/member")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    private void addSampleProduct() {
        final ProductRequest productRequest = new ProductRequest(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", authorization)
            .when()
            .body(productRequest)
            .post("/admin/register")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
