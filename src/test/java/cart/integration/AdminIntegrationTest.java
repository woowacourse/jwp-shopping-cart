package cart.integration;

import static io.restassured.RestAssured.given;

import cart.controller.dto.MemberDto;
import cart.controller.dto.ProductDto;
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

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
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
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=")
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
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=")
            .when()
            .body(productDto)
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
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");
        addSampleProduct();

        // when, then
        given()
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=")
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productDto)
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
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=")
            .when()
            .delete("/admin/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void addAdminMember() {
        final MemberDto journey = new MemberDto(1L, "ADMIN", "journey@gmail.com",
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
        final ProductDto productDto = new ProductDto(1L, "치킨", "chickenUrl", 20000, "KOREAN");

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=")
            .when()
            .body(productDto)
            .post("/admin/register")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
