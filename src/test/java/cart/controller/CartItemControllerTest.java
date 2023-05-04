package cart.controller;

import cart.dto.request.CartItemCreationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql("/testData.sql")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartItemControllerTest {

    private static final String EMAIL = "irene@email.com";
    private static final String PASSWORD = "password1";

    @LocalServerPort
    int port;

    @Nested
    @DisplayName("장바구니에 상품을 추가하는 postCartItems 메서드 테스트")
    class PostTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 등록이 되었다면 상태코드 201을 반환하는지 확인한다")
        @Test
        void successTest() {
            final CartItemCreationRequest request = new CartItemCreationRequest(1L);

            RestAssured.given().log().all()
                    .auth().preemptive().basic(EMAIL, PASSWORD)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/cart/cart-items")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("정상 등록이 되지 않았다면 상태코드 400을 반환하는지 확인한다")
        @Test
        void failTest() {
            final CartItemCreationRequest request = new CartItemCreationRequest(100L);

            RestAssured.given().log().all()
                    .auth().preemptive().basic(EMAIL, PASSWORD)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/cart/cart-items")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("사용자 설정을 하지 않고 장바구니에 상품을 추가하려하면 상태코드 401을 반환하는지 확인한다")
        @Test
        void failTest2() {
            final CartItemCreationRequest request = new CartItemCreationRequest(1L);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/cart/cart-items")
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Nested
    @DisplayName("장바구니에 상품을 조회하는 getCartItems 메서드 테스트")
    class GetTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 조회가 되었다면 상태코드 200을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .auth().preemptive().basic(EMAIL, PASSWORD)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/cart/cart-items")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @DisplayName("사용자 설정을 하지 않고 장바구니를 조회하면 상태코드 401을 반환하는지 확인한다")
        @Test
        void failTest() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/cart/cart-items")
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Nested
    @DisplayName("장바구니에 상품을 삭제하는 deleteCartItems 메서드 테스트")
    class DeleteTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 삭제가 되었다면 상태코드 201을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .auth().preemptive().basic(EMAIL, PASSWORD)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/cart/cart-items/1")
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("사용자 설정을 하지 않고 장바구니를 삭제하려하면 상태코드 401을 반환하는지 확인한다")
        @Test
        void failTest() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/cart/cart-items/1")
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
