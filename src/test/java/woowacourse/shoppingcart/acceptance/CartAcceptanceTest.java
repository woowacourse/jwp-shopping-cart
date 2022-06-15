package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;

@DisplayName("장바구니 관련 기능")
@Sql("/truncate.sql")
public class CartAcceptanceTest extends AcceptanceTest {

    private String token;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        signUp();
        token = getToken(잉_로그인요청);
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        final ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("해당하는 제품이 없는 경우, 장바구니에 제품을 추가할 수 없다.")
    @Test
    void addCartItemWithNotFoundProductShouldFail() {
        final ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1 + productId2);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage()).isEqualTo("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        final Long cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        final Long cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);

        final ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);
        final List<CartResponse> carts = response.as(List.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(carts.size()).isEqualTo(2);
    }

    @DisplayName("장바구니 아이템 수량 수정")
    @Test
    void updateCartItem() {
        int quantity = 12;
        final Long cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        final Long cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);

        final ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(token, productId1, quantity);
        final ExtractableResponse<Response> getResponse = 장바구니_아이템_목록_조회_요청(token);
        final Integer 바뀐_수량 = getResponse.jsonPath().getList("quantity", Integer.class)
                .stream()
                .filter(q -> q.equals(quantity))
                .findFirst().orElseGet(null);

        final List<CartResponse> carts = getResponse.as(List.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(바뀐_수량).isEqualTo(quantity);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        final Long cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        final Long cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);
        final List<Long> cartIds = List.of(cartId1, cartId2);

        final ExtractableResponse<Response> deleteResponse = 장바구니_삭제_요청(token, cartIds);
        final ExtractableResponse<Response> getResponse = 장바구니_아이템_목록_조회_요청(token);
        final List<CartResponse> carts = getResponse.as(List.class);

        장바구니_삭제됨(deleteResponse);
        assertThat(carts.size()).isEqualTo(0);
    }

    @DisplayName("장바구에 해당 제품이 없는 경우, 장바구니에서 제품을 삭제할 수 없다.")
    @Test
    void deleteCartItemWithNotFountProductShouldFail() {
        final Long cartId1 = 장바구니_아이템_추가되어_있음(token, productId1);
        final Long cartId2 = 장바구니_아이템_추가되어_있음(token, productId2);

        final List<Long> cartIds = List.of(cartId1 + cartId2);
        final ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartIds);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.as(ExceptionResponse.class).getMessage()).isEqualTo("장바구니 아이템이 없습니다.");
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수정_요청(String token, Long productId, Integer quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().put("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, List<Long> cartIds) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cartIds", cartIds);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
