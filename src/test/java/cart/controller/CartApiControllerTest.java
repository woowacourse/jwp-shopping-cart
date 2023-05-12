package cart.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import cart.dto.AuthInfoRequest;
import cart.dto.CreateProductRequest;
import cart.service.CartService;
import cart.service.ProductService;
import io.restassured.RestAssured;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql("/init.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartApiControllerTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void 장바구니_페이지를_조회한다() {
        RestAssured
                .given()
                .when().get("/cart")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 유효한_조회_요청이라면_200_응답코드와_상품_목록을_반환한다() {
        final String email = "kokodakadokok@gmail.com";
        final String password = "12345";
        cartService.addProductByAuthInfo(1L, new AuthInfoRequest(email, password));
        productService.addProduct(new CreateProductRequest("name", "imageUrl", 10000));

        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .when().get("/cart/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1))
                .body("[0].name", equalTo("name"))
                .body("[0].imageUrl", equalTo("imageUrl"))
                .body("[0].price", equalTo(10000));
    }

    @Test
    void 유효한_추가_요청이라면_200_응답코드를_반환한다() {
        final String email = "kokodakadokok@gmail.com";
        final String password = "12345";

        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .queryParam("productId", 1)
                .when().post("/cart")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 유효한_삭제_요청이라면_200_응답코드를_반환한다() {
        final String email = "kokodakadokok@gmail.com";
        final String password = "12345";
        cartService.addProductByAuthInfo(1L, new AuthInfoRequest(email, password));
        productService.addProduct(new CreateProductRequest("name", "imageUrl", 10000));

        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .queryParam("productId", 1)
                .when().delete("/cart")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_상품에_대한_삭제_요청이라면_404_응답코드를_반환한다() {
        final String email = "kokodakadokok@gmail.com";
        final String password = "12345";

        RestAssured
                .given()
                .auth().preemptive().basic(email, password)
                .queryParam("productId", 1)
                .when().delete("/cart")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 유효하지_않은_인증_요청이라면_401_응답코드를_반환한다() {
        final String encodedAuthorization = new String(Base64.getEncoder().encode("a:a:a".getBytes()));

        RestAssured
                .given()
                .header("Authorization", "Basic " + encodedAuthorization)
                .when().get("/cart/items")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
