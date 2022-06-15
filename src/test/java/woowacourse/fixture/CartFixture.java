package woowacourse.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.Fixture.delete;
import static woowacourse.fixture.Fixture.get;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;

public class CartFixture {

    private CartFixture() {
    }

    public static ExtractableResponse<Response> 장바구니_상품_추가_요청(String token,
                                                              long customerId,
                                                              long productId,
                                                              long count // 구매 수량
    ) {
        CartItemCreateRequest body = CartItemCreateRequest.from(productId, count);

        return Fixture.post("/api/customers/" + customerId + "/carts", token, body);
    }

    public static ExtractableResponse<Response> 장바구니_상품_목록_조회_요청(String token, long customerId) {
        return get("/api/customers/" + customerId + "/carts", token);
    }

    public static ExtractableResponse<Response> 장바구니_상품_삭제_요청(String token, long customerId, long productId) {
        return delete("/api/customers/" + customerId + "/carts?productId=" + productId, token);
    }

    public static Long 장바구니_상품_추가_요청후_ID_반환(String token, long customerId, long productId, long count) {
        ExtractableResponse<Response> response = 장바구니_상품_추가_요청(token, customerId, productId, count);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static void 장바구니_상품_추가_검증(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void 장바구니_삭제_검증(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
