package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.UserCartRequest;
import cart.dto.UserResponse;
import cart.service.ProductService;
import cart.service.UserService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("장바구니 상품 추가 테스트")
    class AddProductToCart {
        @DisplayName("유저의 장바구니에 상품을 추가할 수 있다")
        @Test
        void addProductToCart() {
            //given
            productService.addProduct(new ProductRequest("연어", "이미지", 10000));
            final ProductResponse product = productService.findProducts().get(0);

            final UserResponse user = userService.findAllUsers().get(0);

            //then
            given()
                    .auth().preemptive().basic(user.getEmail(), user.getPassword())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UserCartRequest(product.getId()))
                    .when().post("/user/cart")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("이메일이 유효하지 않으면 400을 리턴한다")
        @Test
        void invalidUserId() {
            //given
            productService.addProduct(new ProductRequest("연어", "이미지", 10000));
            final ProductResponse product = productService.findProducts().get(0);

            //then
            given()
                    .auth().preemptive().basic("없는 이메일", " ")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UserCartRequest(product.getId()))
                    .when().post("/user/cart")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품이 유효하지 않으면 400을 리턴한다")
        @Test
        void invalidProductId() {
            //given
            final UserResponse user = userService.findAllUsers().get(0);

            //then
            given()
                    .auth().preemptive().basic(user.getEmail(), user.getPassword())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UserCartRequest(0L))
                    .when().post("/user/cart")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("유저의 장바구니에 추가한 상품을 제거할 수 있다")
    @Test
    void deleteProductInCart() {
        //given
        productService.addProduct(new ProductRequest("연어", "이미지", 10000));
        final UserResponse user = userService.findAllUsers().get(0);

        //then
        System.out.println(user);
        given()
                .auth().preemptive().basic(user.getEmail(), user.getPassword())
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/user/cart/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Nested
    @DisplayName("유저 장바구니 조회 테스트")
    class GetAllProducts {
        @DisplayName("유저의 장바구니를 조회하면 200을 리턴한다")
        @Test
        void getAllProducts() {
            //given
            final UserResponse user = userService.findAllUsers().get(0);

            //then
            given()
                    .auth().preemptive().basic(user.getEmail(), user.getPassword())
                    .when().get("user/cart/all")
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("없는 유저의 장바구니를 조회하면 400을 리턴한다")
        @Test
        void notExistUser() {
            given()
                    .auth().preemptive().basic("없는 이메일", "password")
                    .when().get("user/cart/all")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
