package woowacourse.shoppingcart.ui;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

public class CartControllerTest extends AcceptanceTest {

    private static final long CUSTOMER_ID = 1L;

    @Test
    @DisplayName("존재하지 않는 상품 ID를 삭제 요청 하는 경우 404 응답을 반환한다.")
    void deleteCartItem_notFound() {
        //when
        ExtractableResponse<Response> response = 장바구니_아이템_삭제_요청(CUSTOMER_ID, 1L);

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_삭제_요청(long customerId, long productId) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/api/customers/{customerId}/carts?productId={productId}", customerId, productId)
            .then().log().all()
            .extract();
    }
}
