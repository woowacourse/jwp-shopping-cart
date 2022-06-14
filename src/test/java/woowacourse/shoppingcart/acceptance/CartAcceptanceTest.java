package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.utils.Fixture.signupRequest;
import static woowacourse.utils.Fixture.tokenRequest;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.치킨;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;

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
import woowacourse.shoppingcart.dto.cart.CartDeleteRequest;
import woowacourse.shoppingcart.dto.cart.CartProduct;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.utils.AcceptanceTest;

@DisplayName("장바구니 관련 기능 인수 테스트")
public class CartAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        httpPost("/customers", signupRequest);
        ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
        token = loginResponse.jsonPath().getString("accessToken");

        productId1 = 상품_등록되어_있음(치킨, token);
        productId2 = 상품_등록되어_있음(맥주, token);
    }

    @DisplayName("장바구니 아이템 생성")
    @Test
    void createCartItem() {
        CartSetRequest cartSetRequest = new CartSetRequest(100);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1, cartSetRequest);

        장바구니_아이템_생성됨(response);
    }

    @DisplayName("장바구니 아이템 수량 변경")
    @Test
    void updateCartItem() {
        CartSetRequest cartSetRequest = new CartSetRequest(100);
        장바구니_아이템_추가_요청(token, productId1, cartSetRequest);

        CartSetRequest cartSetRequest2 = new CartSetRequest(200);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1, cartSetRequest2);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        CartSetRequest cartSetRequest1 = new CartSetRequest(100);
        CartSetRequest cartSetRequest2 = new CartSetRequest(200);
        장바구니_아이템_추가되어_있음(token, productId1, cartSetRequest1);
        장바구니_아이템_추가되어_있음(token, productId2, cartSetRequest2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        CartSetRequest cartSetRequest1 = new CartSetRequest(100);
        Long cartId = 장바구니_아이템_추가되어_있음(token, productId1, cartSetRequest1);

        CartDeleteRequest cartDeleteRequest = new CartDeleteRequest(List.of(cartId));
        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartDeleteRequest);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long pathVariable, Object object) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .when().put("/cart/products/" + pathVariable)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, Object object) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(object)
                .when().delete("/cart")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String token, Long productId, Object object) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId, object);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartProduct.class).stream()
                .map(CartProduct::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
