package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.RequestFixture.로그인_요청_및_토큰발급;
import static woowacourse.fixture.RequestFixture.상품_등록되어_있음;
import static woowacourse.fixture.RequestFixture.회원가입_요청_및_ID_추출;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.cart.CartItemCountRequest;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, customerId, productId1, 5);
        ExtractableResponse<Response> results = 장바구니_아이템_목록_조회_요청(token, customerId);

        // then
        장바구니_아이템_추가됨(response);
        장바구니_아이템_목록_포함됨(results, productId1);
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 추가 시 예외 발생")
    @Test
    void addCartItem_throwNotLogin() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청("failToken", customerId, productId1, 5);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인된 토큰과 다른 customerId로 장바구니 아이템 추가 시 예외 발생")
    @Test
    void addCartItem_throwForbidden() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(token, customerId + 1, productId1, 5);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        long productId2 = 상품_등록되어_있음("피자", 20_000, "http://example.com/chicken.jpg", 20);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);
        장바구니_아이템_추가_요청(token, customerId, productId2, 3);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token, customerId);

        // then
        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("잘못된 토큰으로 장바구니 아이템 목록 조회 시 예외가 발생")
    @Test
    void getCartItems_throwNotLogin() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        long productId2 = 상품_등록되어_있음("피자", 20_000, "http://example.com/chicken.jpg", 20);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);
        장바구니_아이템_추가_요청(token, customerId, productId2, 3);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청("failToken", customerId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 된 토큰과 다른 ID의 장바구니 아이템 목록 조회 시 예외가 발생")
    @Test
    void getCartItems_throwForbidden() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        long productId2 = 상품_등록되어_있음("피자", 20_000, "http://example.com/chicken.jpg", 20);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);
        장바구니_아이템_추가_요청(token, customerId, productId2, 3);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(token, customerId + 1);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("장바구니 항목 개수 업데이트")
    @Test
    void updateCartItemCount() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_업데이트_요청(token, customerId, productId1, 10);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("잘못된 토큰으로 장바구니 항목 개수 업데이트하려 할 떄 예외가 발생")
    @Test
    void updateCartItemCount_throwNotLogin() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_업데이트_요청("failToken", customerId, productId1, 10);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 된 토큰과 다른 ID의 장바구니 항목 개수 업데이트하려 할 떄 예외가 발생")
    @Test
    void updateCartItemCount_throwForbidden() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_업데이트_요청(token, customerId + 1, productId1, 10);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("장바구니 항목 삭제")
    @Test
    void deleteCartItem() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, customerId, productId1);

        // then
        장바구니_삭제됨(response);
    }

    @DisplayName("잘못된 토큰으로 장바구니 항목 삭제 시 예외 발생")
    @Test
    void deleteCartItem_throwNotLogin() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_삭제_요청("failToken", customerId, productId1);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 된 토큰과 다른 ID의 장바구니 항목 삭제 시 예외 발생")
    @Test
    void deleteCartItem_throwForbidden() {
        // given
        long customerId = 회원가입_요청_및_ID_추출(new CustomerCreateRequest("test@naver.com", "hi", "123456789"));
        String token = 로그인_요청_및_토큰발급(new TokenRequest("test@naver.com", "123456789"));

        long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg", 10);
        장바구니_아이템_추가_요청(token, customerId, productId1, 5);

        // when
        ExtractableResponse<Response> response = 장바구니_삭제_요청(token, customerId + 1, productId1);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String token, Long customerId, Long productId,
                                                               int count) {
        CartItemCreateRequest cartRequest = new CartItemCreateRequest(productId, count);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when().post("/api/customers/{customerId}/carts", customerId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String token, Long customerId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerId}/carts", customerId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_업데이트_요청(String token, Long customerId, Long productId,
                                                                 int count) {
        CartItemCountRequest countRequest = new CartItemCountRequest(count);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(countRequest)
                .when().patch("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String token, Long customerId, Long productId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemDto.class).stream()
                .map(CartItemDto::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
