package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CartItemChangeQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String token = createToken("test1@email.com", "Password123!");

        RestAssured.given().log().all()
          .auth().oauth2(token)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .when().post("/api/carts/products/" + productId1)
          .then().log().all()
          .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String token = createToken("test1@email.com", "Password123!");
        insertCartItem(productId1, token);
        insertCartItem(productId2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, "치킨", "맥주");
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String token = createToken("test1@email.com", "Password123!");
        insertCartItem(productId1, token);

        RestAssured.given().log().all()
          .auth().oauth2(token)
          .delete("/api/carts/products/" + productId1)
          .then()
          .log().all()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 상품 수량 변경")
    @Test
    void updateCartItemQuantity() {
        String token = createToken("test1@email.com", "Password123!");
        insertCartItem(productId1, token);
        CartItemChangeQuantityRequest cartItemChangeQuantityRequest = new CartItemChangeQuantityRequest(2);

        RestAssured.given().log().all()
          .auth().oauth2(token)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(cartItemChangeQuantityRequest)
          .patch("/api/carts/products/" + productId1)
          .then()
          .log().all()
          .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void insertCartItem(Long productId, String token) {

        RestAssured.given().log().all()
          .auth().oauth2(token)
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .when().post("/api/carts/products/" + productId)
          .then().log().all()
          .statusCode(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, String... productIds) {
        List<String> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(CartItemResponse::getName)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }


    private String createToken(String email, String password) {
        TokenRequest tokenRequest = new TokenRequest(email, password);
        TokenResponse tokenResponse = RestAssured.given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .body(tokenRequest)
          .post("/api/login")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }
}
