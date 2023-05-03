package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.CartDao;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {
    @Autowired
    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("DELETE FROM cart");
    }

    @DisplayName("장바구니에 상품 추가")
    @Test
    void addProductToCart() {
        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().post("/cart/{id}", 2)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        ProductEntity product = cartDao.findAllByMemberId(1).get(0);
        assertThat(product.getName()).isEqualTo("피자");
        assertThat(product.getPrice()).isEqualTo(10000);
    }

    @DisplayName("장바구니에 있는 상품 제거")
    @Test
    void deleteProductFromCart() {
        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().post("/cart/{id}", 2)
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().delete("/cart/{id}", 2)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(cartDao.findAllByMemberId(1).size()).isEqualTo(0);
    }

    @DisplayName("장바구니에 있는 상품 전체 조회")
    @Test
    void findAllProductInCart() {
        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().post("/cart/{id}", 2)
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().post("/cart/{id}", 3)
                .then()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(cartDao.findAllByMemberId(1).size()).isEqualTo(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().get("/cart/all")
                .then()
                .body("size()", equalTo(2))
                .body("[0].name", equalTo("피자"))
                .body("[0].price", equalTo(10000))
                .body("[1].name", equalTo("떡볶이"))
                .body("[1].price", equalTo(5000));
    }
}
