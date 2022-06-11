package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static String testToken;

    private Long productId1;
    private Long productId2;

    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        signUpCustomer();
        testToken = login();

        ProductRequest productRequest1 =
                new ProductRequest("치킨", 10_000, "http://example.com/chicken.jpg");
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productResponse1 = ProductResponse.of(productId1, productRequest1);

        ProductRequest productRequest2 =
                new ProductRequest("맥주", 20_000, "http://example.com/beer.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
        productResponse2 = ProductResponse.of(productId2, productRequest2);
    }

    @DisplayName("장바구니에 아이템을 추가한다.")
    @Test
    void addCartItem() {
        장바구니_아이템_추가_요청(productId1);

        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 1);

        장바구니_아이템_목록_조회(List.of(cartItemResponse1));
    }

    @DisplayName("장바구니에 이미 존재하는 아이템을 추가할 경우 예외를 발생시킨다..")
    @Test
    void addCartItem_duplicateItem() {
        장바구니_아이템_중복_추가_요청(productId1);
    }

    @DisplayName("올바르지 않은 아이템을 장바구니에 추가할 경우 예외를 발생시킨다.")
    @Test
    void addCartItem_invalidItem() {
        장바구니_잘못된_아이템_추가_요청(productId2 + 200L);
    }

    @DisplayName("장바구니 아이템 목록을 조회한다.")
    @Test
    void getCartItems() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);

        장바구니_아이템_목록_조회_요청();

        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 1);
        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);

        장바구니_아이템_목록_조회(List.of(cartItemResponse1, cartItemResponse2));
    }

    @DisplayName("장바구니 아이템 수량을 변경한다.")
    @Test
    void updateCartItemQuantity() {
        장바구니_아이템_추가_요청(productId1);
        int expected = 3;
        QuantityRequest quantityRequest = new QuantityRequest(expected);

        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(quantityRequest)
                .when().put("/cart/{productId}/quantity", productId1)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, expected);
        장바구니_아이템_목록_조회(List.of(cartItemResponse1));
    }

    @DisplayName("장바구니에 있는 일부 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);
        ProductIdsRequest productIdsRequest = new ProductIdsRequest(List.of(productId1));
        장바구니_삭제_요청(productIdsRequest)
                .then().log().all();

        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);
        장바구니_아이템_목록_조회(List.of(cartItemResponse2));
    }

    @DisplayName("존재하지 않는 장바구니 아이템을 삭제하려 할 경우 예외를 발생시킨다.")
    @Test
    void deleteCartItem_invalidItem() {
        ProductIdsRequest productIdsRequest = new ProductIdsRequest(List.of(productId1));

        장바구니_삭제_요청(productIdsRequest)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("장바구니의 모든 아이템들을 성공적으로 비운다.")
    @Test
    void deleteCart() {
        장바구니_아이템_추가_요청(productId1);
        장바구니_아이템_추가_요청(productId2);
        장바구니_전부_삭제_요청();

        장바구니_아이템_목록_조회(List.of());
    }

    private static void 장바구니_아이템_추가_요청(Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/cart/{productId}", productId)
                .then().log().all()
                .extract();
    }

    private static void 장바구니_아이템_중복_추가_요청(Long productId) {
        장바구니_아이템_추가_요청(productId);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/cart/{productId}", productId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("redirect", equalTo(true));
    }

    private static void 장바구니_잘못된_아이템_추가_요청(Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/cart/{productId}", productId)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private static void 장바구니_아이템_목록_조회_요청() {
        RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    private static void 장바구니_아이템_목록_조회(List<CartItemResponse> expected) {
        ExtractableResponse<Response> responseCart = RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        List<CartItemResponse> actual = responseCart.jsonPath()
                .getObject(".", CartResponse.class)
                .getCartItems();

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private static Response 장바구니_삭제_요청(ProductIdsRequest productIdsRequest) {
        return RestAssured.given().log().all()
                .body(productIdsRequest)
                .contentType(ContentType.JSON)
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/products");
    }

    private static ExtractableResponse<Response> 장바구니_전부_삭제_요청() {
        return RestAssured.given().log().all()
                .auth().oauth2(testToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers")
                .then().log().all();
    }

    private String login() {
        return RestAssured.given().log().all()
                .body(new TokenRequest("forky", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
