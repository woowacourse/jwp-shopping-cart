package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원가입;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.ProductInCartRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long productId1;
    private Long productId2;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        회원가입(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        token = 로그인(new SignInRequest("rennon@woowa.com", "123456"))
                .as(SignInResponse.class)
                .getToken();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1);

        장바구니_아이템_추가됨(response);
    }

//    @DisplayName("장바구니 아이템 목록 조회")
//    @Test
//    void getCartItems() {
//        장바구니_아이템_추가되어_있음(USER, productId1);
//        장바구니_아이템_추가되어_있음(USER, productId2);
//
//        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(USER);
//
//        장바구니_아이템_목록_응답됨(response);
//        장바구니_아이템_목록_포함됨(response, productId1, productId2);
//    }

//    @DisplayName("장바구니 삭제")
//    @Test
//    void deleteCartItem() {
//        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1);
//
//        ExtractableResponse<Response> response = 장바구니_삭제_요청(USER, cartId);
//
//        장바구니_삭제됨(response);
//    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long productId) {
        ProductInCartRequest productInCartRequest = new ProductInCartRequest(productId, 1, true);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(productInCartRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart")
                .then().log().all()
                .extract();
    }

//    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String userName) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/cart", userName)
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 장바구니_삭제_요청(String userName, Long cartId) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().delete("/api/customers/{customerName}/carts/{cartId}", userName, cartId)
//                .then().log().all()
//                .extract();
//    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        String location = response.header("Location");
        assertThat(location).isNotBlank();
    }

//    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
//        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
//        return Long.parseLong(response.header("Location").split("/carts/")[1]);
//    }

//    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
//        List<Long> resultProductIds = response.jsonPath().getList(".", Cart.class).stream()
//                .map(Cart::getProductId)
//                .collect(Collectors.toList());
//        assertThat(resultProductIds).contains(productIds);
//    }
//
//    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
}
