package cart.controller;

import cart.database.dao.CartDao;
import cart.database.dao.ProductDao;
import cart.database.dao.UserDao;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;

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

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        email = "test@test.com";
        password = "password";
        authHeader = "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());

        userDao.create(new UserEntity(0, email, password));
        productDao.create(new ProductEntity(0, "product1", "test", 1000));
        cartDao.create(1L, 1L, 1);
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
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", authHeader)
                .when().get("/carts")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.OK.value());
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
