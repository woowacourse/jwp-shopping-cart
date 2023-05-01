package cart.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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
public class CartIntegrationTest {

    private String authorization;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        authorization = "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=";

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
        addSampleMember();
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
        addSampleMember();
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
        addSampleMember();
        addSampleProduct();
        addSampleCart();

        // when, then
        given()
            .header("Authorization", authorization)
            .when()
            .delete("/cart/{targetMemberId}/{productId}", 1L, 1L)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void addSampleMember() {
        final MemberDto journey = new MemberDto(1L, "journey@gmail.com", "password", "져니",
            "010-1234-5678");
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
            .when()
            .body(productDto)
            .post("/admin")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    private void addSampleCart() {
        given()
            .header("Authorization", authorization)
            .when()
            .post("/cart/{productId}", 1L)
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value());
    }
}
