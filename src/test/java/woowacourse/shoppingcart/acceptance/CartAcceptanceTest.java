package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.ExistedProductResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "희봉";
    private Long productId1;
    private Long productId2;
    TokenResponse response;
    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        회원_가입(
                회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                        "희봉", "male", "1998-08-07", "12345678910", "address", "detailAddress", "12345", true
                ));

        response =
                로그인_후_토큰_발급(로그인_정보("example@example.com", "example123!"));
        token = "Bearer " + response.getAccessToken();
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg",  "description", 1);
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg",  "description", 1);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(USER, productId1, token);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가되어_있음(USER, productId1, token);
        장바구니_아이템_추가되어_있음(USER, productId2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 특정 아이템 존재 여부 조회")
    @Test
    void doesItemExistInCart() {
        장바구니_아이템_추가되어_있음(USER, productId1, token);
        장바구니_특정_아이템_존재_확인(token, productId1);

    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1, token);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, cartId);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId, final String token) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", 1);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .body(requestBody)
                .when().post("/api/customers/cart")
                .then().log().all()
                .extract();
    }

    public static boolean 장바구니_특정_아이템_존재_확인(String token, Long productId) {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .when().get("/api/customers/cart/" + productId)
                .then().log().all()
                .extract();
        return response.body()
                .jsonPath()
                .getObject("", ExistedProductResponse.class).isExists();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, Long cartId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .when().delete("/api/customers/cart/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId, String token) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId, token);
        return Long.parseLong(response.header("Location").split("/cart/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", Cart.class).stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private SignUpRequest 회원_정보(String email, String password, String profileImageUrl, String name, String gender,
                                String birthday, String contact, String address, String detailAddress,
                                String zoneCode,
                                boolean terms) {
        return new SignUpRequest(email, password, profileImageUrl, name, gender, birthday, contact, address,
                detailAddress, zoneCode, terms);
    }

    private ExtractableResponse<Response> 회원_가입(SignUpRequest request) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/customers")
                .then()
                .log().all()
                .extract();
    }

    private TokenResponse 로그인_후_토큰_발급(TokenRequest request) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
        return response.body()
                .jsonPath()
                .getObject("", TokenResponse.class);
    }

    private TokenRequest 로그인_정보(final String email, final String password) {
        return new TokenRequest(email, password);
    }

}
