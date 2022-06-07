package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

public class CartControllerTest extends AcceptanceTest {

    private static final long CUSTOMER_ID = 1L;

    @Test
    @DisplayName("없는 상품 ID로 아이템 추가 요청할 경우 404 응답을 반환한다.")
    void addCartItem_notFound() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(CUSTOMER_ID, 99L, 7);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("이미 추가된 상품을 다시 추가 요청할 경우 400 응답을 반환한다.")
    void addCartItem_alreadyInStock() {
        //when
        장바구니_아이템_추가_요청(CUSTOMER_ID, 1L, 7);
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(CUSTOMER_ID, 1L, 7);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("아이템 추가 요청이 재고보다 클 경우 400 응답을 반환한다.")
    void addCartItem_overQuantity() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(CUSTOMER_ID, 1L, 11);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID를 삭제 요청 하는 경우 404 응답을 반환한다.")
    void deleteCartItem_notFound() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_삭제_요청(CUSTOMER_ID, 99L);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("구매 수량 업데이트 요청이 재고보다 클 경우 400 응답을 반환한다.")
    void updateCount_overQuantity() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_구매_수_업데이트(CUSTOMER_ID, 1L, 11);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("없는 상품 ID로 구매 수량 업데이트 요청할 경우 404 응답을 반환한다.")
    void updateCount_notFound() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_구매_수_업데이트(CUSTOMER_ID, 99L, 7);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(long customerId, Long productId, int count) {
        CartItemRequest request = new CartItemRequest(productId, count);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/customers/{customerId}/carts", customerId)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_삭제_요청(long customerId, long productId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 장바구니_아이템_구매_수_업데이트(long customerId, Long productId, int count) {
        CartItemUpdateRequest request = new CartItemUpdateRequest(count);
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().patch("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
            .then().log().all()
            .extract();
    }
}
