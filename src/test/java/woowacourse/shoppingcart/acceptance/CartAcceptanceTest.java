package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.auth.support.AuthorizationExtractor.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.support.SimpleRestAssured;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String USER = "puterism";

    @Autowired
    private JwtTokenProvider provider;

    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        final ExtractableResponse<Response> response = CustomerAcceptanceFixture.saveCustomerWithName(USER);
        Long id = SimpleRestAssured.getId(response);

        token = BEARER_TYPE + provider.createToken(id.toString());
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(token, productId1);
        장바구니_아이템_추가되어_있음(token, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(token, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", 10);

        return RestAssured
            .given().log().all()
            .header(new Header("Authorization", token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(requestBody)
            .when().post("/api/customers/me/cart-items")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
            .given().log().all()
            .header(new Header("Authorization", token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/api/customers/me/cart-items")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, Long cartId) {
        return RestAssured
            .given().log().all()
            .header(new Header("Authorization", token))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/customers/me/cart-items/{cartId}", cartId)
            .then().log().all()
            .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId);
        return Long.parseLong(response.header("Location").split("/cart-items/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        final List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class)
            .stream()
            .map(cartItemResponse -> cartItemResponse.getProduct().getId())
            .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
