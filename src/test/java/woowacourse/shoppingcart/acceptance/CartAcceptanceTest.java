package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.로그인;
import static woowacourse.shoppingcart.acceptance.CustomerAcceptanceTest.회원가입;
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
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.request.CartIdRequest;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.request.DeleteProductRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequests;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.CartResponse;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
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

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        장바구니_아이템_추가_요청(token, productId1);
        장바구니_아이템_추가_요청(token, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, "치킨", "맥주");
    }

    @Test
    @DisplayName("장바구니 아이템 정보 수정")
    void updateCartItems() {
        장바구니_아이템_추가_요청(token, productId1);
        장바구니_아이템_추가_요청(token, productId2);

        UpdateCartRequests updateCartRequests = new UpdateCartRequests(
                List.of(new UpdateCartRequest(1L, 2, false), new UpdateCartRequest(2L, 3, true)));
        ExtractableResponse<Response> response = 장바구니_아이템_수정_요청(token, updateCartRequests);

        장바구니_수정됨(response);
    }

    @Test
    @DisplayName("장바구니 일부 삭제")
    void deleteCartItem() {
        장바구니_아이템_추가_요청(token, productId1);
        장바구니_아이템_추가_요청(token, productId2);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(token,
                new DeleteProductRequest(List.of(new CartIdRequest(1L))));

        장바구니_삭제됨(response);
    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    void deleteAllCartItem() {
        장바구니_아이템_추가_요청(token, productId1);
        장바구니_아이템_추가_요청(token, productId2);

        ExtractableResponse<Response> response = 장바구니_전체_삭제_요청(token);

        장바구니_삭제됨(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long productId) {
        CartRequest cartRequest = new CartRequest(productId, 1, true);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(cartRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart")
                .then().log().all()
                .extract();

    }

    public static ExtractableResponse<Response> 장바구니_아이템_수정_요청(String token, UpdateCartRequests updateCartRequests) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(updateCartRequests)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 장바구니_삭제_요청(String token, DeleteProductRequest deleteProductRequest) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .body(deleteProductRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_전체_삭제_요청(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/all")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        String location = response.header("Location");
        assertThat(location).isNotBlank();
    }

    private void 장바구니_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, String... productNames) {
        List<String> resultProductIds = response
                .as(CartResponse.class)
                .getCartItems()
                .stream()
                .map(CartItemResponse::getName)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productNames);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
