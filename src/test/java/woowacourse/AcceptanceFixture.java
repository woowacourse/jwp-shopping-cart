package woowacourse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.Customer;

public class AcceptanceFixture {

    public static ExtractableResponse<Response> createCustomer(Customer customer) {
        final Map<String, String> params = new HashMap<>();
        params.put("loginId", customer.getLoginId());
        params.put("name", customer.getName());
        params.put("password", customer.getPassword());

        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> login(String loginId, String password) {
        return RestAssured
                .given().log().all()
                .body(new TokenRequest(loginId, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();
    }
}
