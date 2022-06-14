package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemFixture {

    public static ExtractableResponse<Response> addCartItems(final String token, final List<Long> productIds) {
        List<Map<String, Object>> requestBody = new ArrayList<>();
        for (Long productId : productIds) {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("id", productId);
            requestBody.add(productInfo);
        }

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/auth/customer/cartItems")
                .then().log().all()
                .extract();
    }
}
