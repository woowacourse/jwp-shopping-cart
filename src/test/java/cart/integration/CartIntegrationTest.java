package cart.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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
public class CartIntegrationTest {

    private String authorization;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        authorization = "Basic am91cm5leUBnbWFpbC5jb206Y0dGemMzZHZjbVE9";

    }

    @Test
    @DisplayName("장바구니 페이지를 조회한다.")
    void getCarts() {
        given()
            .when()
            .get("/cart")
            .then().log().all()
            .contentType(ContentType.HTML)
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    @Sql("classpath:init.sql")
    void addCart() {
        // given
        addAdminMember();
        addSampleProduct();

        // when, then
        given()
            .header("Authorization", authorization)
            .when()
            .post("/cart/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("사용자의 장바구니 정보를 조회한다.")
    @Sql("classpath:init.sql")
    void getCartByMember() {
        addAdminMember();
        addSampleProduct();
        addSampleCart();

        given()
            .header("Authorization", authorization)
            .when()
            .get("/cart/me")
            .then().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .statusCode(HttpStatus.OK.value())
            .body("size()", is(1));
    }

    @Test
    @DisplayName("사용자의 장바구니 상품을 제거한다.")
    @Sql("classpath:init.sql")
    void deleteCart() {
        // given
        addAdminMember();
        addSampleProduct();
        addSampleCart();

        // when, then
        given()
            .header("Authorization", authorization)
            .when()
            .delete("/cart/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void addAdminMember() {
        final MemberRequest journey = new MemberRequest(1L, "ADMIN",
            "journey@gmail.com", "password", "져니", "010-1234-5678");

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

    private void addSampleCart() {
        given()
            .header("Authorization", authorization)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/cart/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
