package woowacourse.shoppingcart.product.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.support.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class ProductRestHandler extends RestHandler {

    private static final String BASE_URL = "/products";

    public static ExtractableResponse<Response> 상품목록() {
        return getRequest(BASE_URL);
    }

    public static ExtractableResponse<Response> 상품조회(final long id) {
        return getRequest(BASE_URL + "/" + id);
    }

    public static <T> void assertThatProductException(final ExtractableResponse<Response> response,
                                                   final ProductExceptionCode exceptionCode) {
        RestHandler.assertThatException(response, exceptionCode);
    }
}
