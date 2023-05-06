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
public class ShoppingIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 리스트를 조회한다.")
    void getProducts() {
        given()
            .when()
            .get("/")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("단일 상품을 조회한다.")
    @Sql("classpath:init.sql")
    void getProduct() {
        // given
        addAdminMember();
        addSampleProduct();

        // when, then
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/{id}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
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
            .header("Authorization", "Basic am91cm5leUBnbWFpbC5jb206Y0dGemMzZHZjbVE9")
            .when()
            .body(productRequest)
            .post("/admin/register")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
