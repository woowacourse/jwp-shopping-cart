package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static woowacourse.fixture.CartItemFixture.createCartItems;
import static woowacourse.fixture.CustomerFixture.login;
import static woowacourse.fixture.CustomerFixture.signUp;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cartitem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemDeleteRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {
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
    void createCartItem() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");

        //when & then
        List<CartItemCreateRequest> cartItemCreateRequests =
                List.of(new CartItemCreateRequest(productId1), new CartItemCreateRequest(productId2));

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequests)
                .when().post("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1, 2))
                .body("addedQuantity", hasItems(1, 1));
    }

    @DisplayName("존재하지 않는 상품을 장바구니에 추가하면 상태코드 401을 반환한다.")
    @Test
    void createCartItemDuplicateProduct() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        Long notExistProductId = 12L;

        //when & then
        List<CartItemCreateRequest> cartItemCreateRequests =
                List.of(new CartItemCreateRequest(notExistProductId), new CartItemCreateRequest(productId2));

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequests)
                .when().post("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("해당 아이디의 상품이 존재하지 않습니다."));
    }

    @DisplayName("장바구니에 기존에 존재하는 물건의 개수 추가")
    @Test
    void addCartItem() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        createCartItems(List.of(productId1, productId2), token);

        //when & then
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L, 3);

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemAddRequest)
                .when().patch("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("quantity", is(3));
    }

    @DisplayName("장바구니에 존재하지 않는 물건의 개수를 추가하려 하면 상태코드")
    @Test
    void addNotExistCartItem() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        createCartItems(List.of(productId1, productId2), token);

        //when & then
        CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(4L, 3);

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemAddRequest)
                .when().patch("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("선택한 장바구니의 상품중 존재하지 않는 상품이 있습니다."));
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        createCartItems(List.of(productId1, productId2), token);

        //when & then
        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1, 2))
                .body("productId", hasItems(1, 2))
                .body("name", hasItems("치킨", "맥주"))
                .body("imageUrl", hasItems("http://example.com/chicken.jpg", "http://example.com/beer.jpg"))
                .body("price", hasItems(10000, 20000))
                .body("quantity", hasItems(1, 1));
    }

    @DisplayName("장바구니 물건 삭제")
    @Test
    void deleteCartItems() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        createCartItems(List.of(productId1, productId2), token);

        //when & then
        List<CartItemDeleteRequest> cartItemDeleteRequests =
                List.of(new CartItemDeleteRequest(1L), new CartItemDeleteRequest(2L));

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemDeleteRequests)
                .when().delete("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 담기지 않은 물건을 삭제하면 401 상태코드를 반환한다.")
    @Test
    void deleteCartItemsNotExistsItem() {
        //given
        signUp("test@naver.com", "리버", "1234!adff");
        ExtractableResponse<Response> response = login("test@naver.com", "1234!adff");
        String token = response.body().jsonPath().getString("accessToken");
        createCartItems(List.of(productId1, productId2), token);

        //when & then
        List<CartItemDeleteRequest> cartItemDeleteRequests =
                List.of(new CartItemDeleteRequest(3L), new CartItemDeleteRequest(2L));

        RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemDeleteRequests)
                .when().delete("/auth/customer/cartItems")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("선택한 장바구니의 상품중 존재하지 않는 상품이 있습니다."));
    }
}
