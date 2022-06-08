package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인_토큰_발급;
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
import woowacourse.shoppingcart.ui.customer.dto.request.CustomerRegisterRequest;

@DisplayName("장바구니 관련 기능")
class CartAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "1234567891";

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem() {
        // given 회원가입 후 로그인하여
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();

        // when 장바구니에 상품을 담으면
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(accessToken, productId1);

        // then 정상적으로 상품이 추가된다.
        장바구니_상품_추가됨(response);
    }

    @Test
    @DisplayName("장바구니 상품 추가시 존재하지 않는 상품일 경우 404 응답을 반환한다.")
    void addCartItem_invalidProductId_returnsNotFound() {
        //given 회원가입 후 로그인하여
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();

        // when 존재하지 않는 상품을 장바구니에 담으면
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(accessToken, 1000L);

        // then NOT_FOUND 를 응답한다.
        요청이_NOT_FOUND_응답함(response);
    }

    @Test
    @DisplayName("회원의 장바구니에 담긴 상품 목록을 조회한다.")
    void getCartItems() {
        //given 회원가입 후 로그인하여
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();
        장바구니_상품_추가_요청(accessToken, productId1);
        장바구니_상품_추가_요청(accessToken, productId2);

        //when
        final ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원의 장바구니에서 상품을 삭제한다.")
    @Test
    void deleteCartItem() {
        //given 회원가입 후 로그인하여 장바구니에 상품을 담고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();
        final Long cartId1 = 장바구니_상품_추가되어_있음(accessToken, productId1);
        final Long cartId2 = 장바구니_상품_추가되어_있음(accessToken, productId2);

        // when 상품을 삭제하면
        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, List.of(cartId1, cartId2));

        // then 정상적으로 상품이 삭제된다.
        장바구니_삭제됨(response);
    }

    @Test
    @DisplayName("존재하지 않는 장바구니 상품을 삭제할 경우 404 NOT_FOUND 를 응답한다..")
    void deleteCartItem_invalidProduct_returnsNotFound() {
        //given 회원가입 후 로그인하여 장바구니에 상품을 담고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();

        // when 상품을 삭제하면
        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, List.of(1L, 2L));

        // then NOT_FOUND 를 응답한다.
        요청이_NOT_FOUND_응답함(response);
    }

    @Test
    @DisplayName("장바구니 상품의 수량을 변경한다.")
    void updateCartItemQuantity() {
        //given 회원가입 후 로그인하여 장바구니에 상품을 담고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = 로그인_토큰_발급();
        장바구니_상품_추가되어_있음(accessToken, productId1);

        //when 장바구니 상품의 수량을 변경하면
        final ExtractableResponse<Response> response = 장바구니_수정_요청(accessToken, productId1, 10);

        //then 성공적으로 수량이 변경된다.
        장바구니_수정됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_상품_추가_요청(final String accessToken, final Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(final String accessToken, final List<Long> cartIds) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cartIds", cartIds);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_수정_요청(final String accessToken, final Long productId,
                                                           final int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/customer/carts")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_상품_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_상품_추가되어_있음(final String accessToken, final Long productId) {
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(accessToken, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
