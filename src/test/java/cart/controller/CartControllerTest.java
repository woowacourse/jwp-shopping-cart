package cart.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import cart.dto.cart.CartProductResponse;
import cart.dto.product.ProductCreateRequest;
import cart.dto.user.UserRequest;
import cart.service.CartService;
import cart.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/dropTable.sql", "/data.sql"})
class CartControllerTest {

    public static final String EMAIL = "ahdjd5@gmail.com";
    public static final String PASSWORD = "qwer1234";
    public static final ProductCreateRequest PRODUCT_CREATE_REQUEST = new ProductCreateRequest("치킨", 10000, "img");
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;

    @BeforeEach
    void setUp(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/cart get요청에 200을 응답한다.")
    void cart() {
        final ExtractableResponse<Response> response =
                given().log().all()
                        .auth().preemptive().basic(EMAIL, PASSWORD)
                        .when().get("/cart")
                        .then().log().all()
                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/cart/products get요청을 받으면, 해당 회원의 장바구니에 담긴 상품을 반환한다.")
    void products() {
        final Long productId = productService.save(PRODUCT_CREATE_REQUEST);
        cartService.addProduct(new UserRequest(EMAIL, PASSWORD), productId);

        final ExtractableResponse<Response> response =
                given().log().all()
                        .auth().preemptive().basic(EMAIL, PASSWORD)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .when().get("/cart/products")
                        .then().log().all()
                        .extract();

        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getString("[0].name")).isEqualTo("치킨"),
                () -> assertThat(response.jsonPath().getInt("[0].price")).isEqualTo(10000),
                () -> assertThat(response.jsonPath().getString("[0].imgUrl")).isEqualTo("img")
        );
    }

    @Test
    @DisplayName("/product/{productId} post 요청 시 cart에 상품을 추가한다.")
    void addProductToCart() {
        //given
        final Long productId = productService.save(PRODUCT_CREATE_REQUEST);

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/cart/product/" + productId)
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("/cart/product/{cartId} delete 요청 시 cart에 담긴 상품을 삭제한다.")
    void removeProductInCart() {
        //given
        final Long productId = productService.save(PRODUCT_CREATE_REQUEST);
        final Long cartId = cartService.addProduct(new UserRequest(EMAIL, PASSWORD), productId);

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .delete("/cart/product/" + cartId)
                .then().log().all()
                .extract();
        final List<CartProductResponse> results = cartService.findAllProductsInCart(
                new UserRequest(EMAIL, PASSWORD));

        //then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(results.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("등록 되지 않은 사용자 정보를 통해 /cart/product*/** url에 요청을 보내면 401을 반환한다.")
    void unauthorizedRequest() {
        //given
        final String unauthorizedEmail = "aaa@aaa.com";

        //when
        final ExtractableResponse<Response> response =
                given().log().all()
                        .auth().preemptive().basic(unauthorizedEmail, PASSWORD)
                        .when().get("/cart/products")
                        .then().log().all()
                        .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
