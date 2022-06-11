package woowacourse.shoppingcart.cart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.cart.application.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.cart.application.dto.request.CartPutRequest;
import woowacourse.shoppingcart.cart.support.exception.CartExceptionCode;
import woowacourse.support.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class CartRestHandler extends RestHandler {

    private static final String BASE_URL = "/cart";

    public static ExtractableResponse<Response> 장바구니담기(final long productId, final CartPutRequest request,
                                                       final String accessToken) {
        return putRequest(BASE_URL + "/products/" + productId, request, accessToken);
    }

    public static ExtractableResponse<Response> 장바구니삭제(final CartDeleteRequest request, final String accessToken) {
        return deleteRequest(BASE_URL, request, accessToken);
    }

    public static ExtractableResponse<Response> 장바구니조회(final String accessToken) {
        return getRequest(BASE_URL, accessToken);
    }

    public static <T> void assertThatCartException(final ExtractableResponse<Response> response,
                                                   final CartExceptionCode exceptionCode) {
        RestHandler.assertThatException(response, exceptionCode);
    }
}
