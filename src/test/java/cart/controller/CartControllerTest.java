package cart.controller;

import cart.dto.CartProductResponse;
import cart.dto.CartRequest;
import cart.dto.ProductRequest;
import cart.service.CartService;
import cart.service.ProductService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {
        AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class CartControllerTest {
    private static final String EMAIL = "aaaaa@aaa.aaa";
    private static final String PASSWORD = "aaaa";

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("인증헤더에 이메일과 비밀번호가 설정이 안되어있을때 401 오류가 난다.")
    @Test
    void failWhenNoHeaderMailAndPassword() {
        RestAssured
                .given().log().all()
                .when().get("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("인증헤더에 이메일과 비밀번호가 설정되어있다면 장바구니 페이지를 띄울 수 있음")
    @Test
    void successWhenHeaderEmailAndPassword() {
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

    }

    @DisplayName("인증헤더에 이메일과 비밀번호가 설정되어있다면 장바구니 페이지를 띄울 수 있음 - 장바구니 내용확인")
    @Test
    void validateWhenHeaderEmailAndPassword() {
        ProductRequest productRequest = new ProductRequest("케로로", 1000,
                "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        productService.addProduct(productRequest);

        CartRequest cartRequest = new CartRequest(1);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        List<CartProductResponse> cartProducts = RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath()
                .getList(".", CartProductResponse.class);

        assertAll(
                () -> assertThat(cartProducts).hasSize(1),
                () -> assertThat(cartProducts).extracting("product.name", "product.price", "product.image")
                        .contains(tuple(productRequest.getName(), productRequest.getPrice(), productRequest.getImage()))
        );

    }

    @Test
    @DisplayName("인증헤더에 이메일과 비밀번호가 설정되어있다면 장바구니에 추가할 수 있음")
    void addCartProduct() {

        CartRequest cartRequest = new CartRequest(1);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    @Test
    @DisplayName("aaaaa@aaa.aaa 이메일로 추가되어있는 장바구니를 aaaaa@aaa.aaa 이메일 권한으로 지우면 지워진다")
    void deleteCartProduct() {
        CartRequest cartRequest = new CartRequest(1);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/carts/" + 1 + "/products")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();

    }

    @Test
    @DisplayName("aaaaa@aaa.aaa 이메일로 추가되어있는 장바구니를 다른 이메일 권한으로 지우려 하면 존재하지 않는 장바구니이다.")
    void failDeleteCartProductByDifferentEmail() {
        CartRequest cartRequest = new CartRequest(1);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/carts/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("bbbbb@bbb.bbb", PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/carts/" + 1 + "/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();

    }

    @Test
    @DisplayName("존재하지 않는 장바구니를 지우려하면 오류가 발생한다.")
    void failDeleteCartProductByNoExistCart() {
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/carts/" + 1 + "/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract();

    }
}