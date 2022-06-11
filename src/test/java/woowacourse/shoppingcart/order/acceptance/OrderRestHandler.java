package woowacourse.shoppingcart.order.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.order.application.dto.request.OrderRequest;
import woowacourse.shoppingcart.order.support.exception.OrderExceptionCode;
import woowacourse.support.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class OrderRestHandler extends RestHandler {

    private static final String BASE_URL = "/orders";

    public static ExtractableResponse<Response> 주문(final OrderRequest request, final String accessToken) {
        return postRequest(BASE_URL, request, accessToken);
    }

    public static ExtractableResponse<Response> 주문조회(final long orderId, final String accessToken) {
        return getRequest(BASE_URL + "/" + orderId, accessToken);
    }

    public static <T> void assertThatOrderException(final ExtractableResponse<Response> response,
                                                    final OrderExceptionCode exceptionCode) {
        RestHandler.assertThatException(response, exceptionCode);
    }
}
