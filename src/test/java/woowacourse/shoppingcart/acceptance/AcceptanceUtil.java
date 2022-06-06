package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AcceptanceUtil {

    private AcceptanceUtil() {
    }

    public static String findValue(ExtractableResponse<Response> response, String value) {
        return response.body().jsonPath().getString(value);
    }
}
