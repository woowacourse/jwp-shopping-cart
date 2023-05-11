package cart.cart;

import cart.auth.dao.JdbcUserDAO;
import cart.cart.dao.CartDAOImpl;
import cart.catalog.dto.ProductResponseDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    public static final String EMAIL = "echo@wtc.com";
    public static final String PASSWORD = "#abcd1234";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("장바구니에 상품 추가 테스트")
    void insert() {

        final int initialSize = RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .get("/cart/items")
                .then()
                .extract()
                .jsonPath()
                .getList(".", ProductResponseDTO.class)
                .size();

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .post("/cart/items/1")
                .then()
                .log().all();

        final int finalSize = RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .get("/cart/items")
                .then()
                .extract()
                .jsonPath()
                .getList(".", ProductResponseDTO.class)
                .size();

        Assertions.assertEquals(initialSize + 1, finalSize);
    }

    @Test
    @DisplayName("장바구니에 상품 삭제 테스트")
    void delete() {


        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .post("/cart/items/1")
                .then()
                .log().all();

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .post("/cart/items/2")
                .then()
                .log().all();

        final int intialSize = RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .get("/cart/items")
                .then()
                .extract()
                .jsonPath()
                .getList(".", ProductResponseDTO.class)
                .size();

        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .delete("/cart/items/1")
                .then()
                .log().all();

        final int finalSize = RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .get("/cart/items")
                .then()
                .extract()
                .jsonPath()
                .getList(".", ProductResponseDTO.class)
                .size();

        Assertions.assertEquals(intialSize - 1, finalSize);
    }

    @Test
    @DisplayName("장바구니 없는 상품 추가 예외 처리 테스트")
    void insertException() {
        RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .post("/cart/items/100")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("장바구니에 없는 상품 삭제 예외 처리 테스트")
    void deleteException() {
        RestAssured.given()
                .log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when()
                .delete("/cart/items/100")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(is(CartDAOImpl.CART_NOT_FOUND_ERROR));

    }

    @Test
    @DisplayName("없는 유저 장바구니 조회 예외 처리 테스트")
    void getCartException() {
        RestAssured.given()
                .auth().preemptive().basic("hello@example.com", "#abcd12345")
                .when()
                .get("/cart/items")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(is(JdbcUserDAO.USER_DOES_NOT_EXISTS_ERROR));
    }

    @Test
    @DisplayName("헤더가 없는 경우 예외 처리 테스트")
    void getCartException2() {
        RestAssured.given()
                .when()
                .get("/cart/items")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
