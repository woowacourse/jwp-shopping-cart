package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.CartDao;
import cart.dto.CartProductAddRequestDto;
import cart.dto.CartProductRemoveRequestDto;
import cart.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
        CartProductAddRequestDto addRequest = new CartProductAddRequestDto(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(ContentType.JSON)
                .body(addRequest)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        ProductEntity product = cartDao.findAllByMemberId(1).get(0);
        assertThat(product.getName()).isEqualTo("피자");
        assertThat(product.getPrice()).isEqualTo(10000);
    }

    @DisplayName("장바구니에 있는 상품 제거")
    @Test
    void deleteProductFromCart() {
        CartProductAddRequestDto addRequest = new CartProductAddRequestDto(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(ContentType.JSON)
                .body(addRequest)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        CartProductRemoveRequestDto removeRequest = new CartProductRemoveRequestDto(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(ContentType.JSON)
                .body(removeRequest)
                .when().delete("/carts")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(cartDao.findAllByMemberId(1).size()).isEqualTo(0);
    }

    @DisplayName("장바구니에 있는 상품 전체 조회")
    @Test
    void findAllProductInCart() {
        CartProductAddRequestDto addRequest1 = new CartProductAddRequestDto(1);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(ContentType.JSON)
                .body(addRequest1)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        CartProductAddRequestDto addRequest2 = new CartProductAddRequestDto(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(ContentType.JSON)
                .body(addRequest2)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());


        assertThat(cartDao.findAllByMemberId(1).size()).isEqualTo(2);

        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().get("/carts")
                .then()
                .body("size()", equalTo(2))
                .body("[0].name", equalTo("치킨"))
                .body("[0].price", equalTo(20000))
                .body("[1].name", equalTo("피자"))
                .body("[1].price", equalTo(10000));
    }
}
