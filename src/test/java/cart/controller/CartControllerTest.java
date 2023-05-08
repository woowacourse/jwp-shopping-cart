package cart.controller;

import cart.dto.ProductId;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CartControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 헤더_인증_정보_없을_때_에러_발생_확인_테스트() {
        RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "")
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 장바구니_목록_GET_API_테스트() {
        RestAssured
                .given().log().all()
                .auth().preemptive().basic("jena@naver.com", "1234")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 장바구니_추가_POST_API_테스트() {
        final ProductId productId = new ProductId(1L);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("jena@naver.com", "1234")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productId)
                .when().post("/carts/product")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/carts/product/1");
    }

    @Test
    void 장바구니_목록_삭제_DELETE_API_테스트() {
        final ProductId productId = new ProductId(1L);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("jena@naver.com", "1234")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productId)
                .when().post("/carts/product")
                .then().log().all();

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("jena@naver.com", "1234")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productId)
                .when().delete("/carts/product/" + productId.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
