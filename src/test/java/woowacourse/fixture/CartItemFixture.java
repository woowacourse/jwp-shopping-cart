package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.cartitem.CartItemCreateRequest;

public class CartItemFixture {

    public static ExtractableResponse<Response> createCartItems(final List<Long> productIds, final String token) {
        List<CartItemCreateRequest> cartItemCreateRequests = productIds.stream()
                .map(CartItemCreateRequest::new)
                .collect(Collectors.toList());

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequests)
                .when().post("/auth/customer/cartItems")
                .then().log().all()
                .extract();
    }
}
