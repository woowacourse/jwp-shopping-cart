package cart.controller;

import cart.controller.dto.response.CartItemResponse;
import cart.database.dao.CartDao;
import cart.database.dao.ProductDao;
import cart.database.dao.UserDao;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartDao cartDao;

    private String email;
    private String password;
    private String authHeader;
    private String productName;
    private String imageUrl;
    private int price;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        email = "test@test.com";
        password = "password";
        authHeader = "Basic " + Base64.encodeBase64String((email + ":" + password).getBytes());

        productName = "product1";
        imageUrl = "test";
        price = 1000;

        userDao.create(new UserEntity(0, email, password));
        productDao.create(new ProductEntity(0, productName, imageUrl, price));
        cartDao.create(userDao.findByEmailAndPassword(email, password).getId(), 1L, 1);
    }

    @DisplayName("장바구니 페이지 접속")
    @Test
    void cartPage() {
        RestAssured.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @DisplayName("상품을 장바구니에 추가")
    @Test
    void addCartItem() {
        Long id = 1L;

        RestAssured.given().log().all()
                .header("Authorization", authHeader)
                .when().post("/carts/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 상품들 조회")
    @Test
    void showCartItems() {
        ValidatableResponse response = RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", authHeader)
                .when().get("/carts")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.OK.value());

        List<CartItemResponse> cartItemResponses = response.extract().body().jsonPath().getList("", CartItemResponse.class);
        CartItemResponse lastOne = cartItemResponses.get(cartItemResponses.size() - 1);
        assertAll(
                () -> assertEquals(productName, lastOne.getName()),
                () -> assertEquals(imageUrl, lastOne.getImageUrl()),
                () -> assertEquals(price, lastOne.getPrice())
        );
    }

    @DisplayName("장바구니 상품 삭제")
    @Test
    void deleteCartItem() {
        RestAssured.given().log().all()
                .header("Authorization", authHeader)
                .when().delete("/carts/" + 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
