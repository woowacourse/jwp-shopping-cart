package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static woowacourse.auth.support.AuthorizationExtractor.AUTHORIZATION;
import static woowacourse.auth.support.AuthorizationExtractor.BEARER_TYPE;
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
import woowacourse.shoppingcart.dto.CartHasProductDto;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.fixture.ProductFixtures;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private Long productId1;
    private Long productId2;
    public String token;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        token = 회원가입_후_로그인_후_토큰_발급();
        productId1 = 상품_등록되어_있음(ProductFixtures.PRODUCT_1);
        productId2 = 상품_등록되어_있음(ProductFixtures.PRODUCT_2);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, productId1);
        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니에 아이템이 있는지 확인")
    @Test
    void hasCartItem() {
        장바구니_아이템_추가_요청(token, productId1);
        ExtractableResponse<Response> response = 장바구니에_아이템_있는지_확인(token, productId1);

        boolean exists = response.jsonPath().getObject("", CartHasProductDto.class).getExists();

        assertTrue(exists);
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

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", 1);

        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니에_아이템_있는지_확인(String accessToken, Long productId) {

        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/carts/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured.given()
                .log().all()
                .header(AUTHORIZATION, BEARER_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartResponse.class).stream()
                .map(cartResponse -> cartResponse.getProduct().getProductId())
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private SignUpRequest 회원_정보(String email, String password, String profileImageUrl, String name,
                                String gender,
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

    private String 회원가입_후_로그인_후_토큰_발급() {
        회원_가입(회원_정보("example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "희봉", "male", "1998-08-07", "12345678910",
                "address", "detailAddress", "12345", true));

        TokenRequest tokenRequest = new TokenRequest("example@example.com", "example123!");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenRequest)
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();
        return response.body()
                .jsonPath()
                .getObject("", TokenResponse.class).getAccessToken();
    }
}
