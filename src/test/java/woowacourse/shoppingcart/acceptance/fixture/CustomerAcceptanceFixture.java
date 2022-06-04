package woowacourse.shoppingcart.acceptance.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.support.SimpleRestAssured;

public class CustomerAcceptanceFixture {

    private static final String DEFAULT_NAME = "username";

    public static ExtractableResponse<Response> saveCustomer() {
        CustomerRequest request = createRequest(null);
        return SimpleRestAssured.post("/api/customers", request);
    }

    public static ExtractableResponse<Response> saveCustomer(String name) {
        final CustomerRequest request = createRequest(name);
        return SimpleRestAssured.post("/api/customers", request);
    }

    private static CustomerRequest createRequest(String name) {
        if (name == null) {
            name = DEFAULT_NAME;
        }
        return new CustomerRequest(
            name,
            "password12!@",
            "example@example.com",
            "some-address",
            "010-0000-0001"
        );
    }
}
