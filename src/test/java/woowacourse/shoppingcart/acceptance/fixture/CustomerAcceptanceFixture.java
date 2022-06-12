package woowacourse.shoppingcart.acceptance.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.support.SimpleRestAssured;

public class CustomerAcceptanceFixture {

    private static final String DEFAULT_NAME = "username";
    private static final String DEFAULT_EMAIL = "sample@email.com";

    public static ExtractableResponse<Response> saveCustomer() {
        CustomerRequest request = createRequest(null, null);
        return SimpleRestAssured.post("/api/customers", request);
    }

    public static ExtractableResponse<Response> saveCustomerWithName(String name) {
        final CustomerRequest request = createRequest(name, null);
        return SimpleRestAssured.post("/api/customers", request);
    }

    public static ExtractableResponse<Response> saveCustomerWithEmail(String email) {
        final CustomerRequest request = createRequest(null, email);
        return SimpleRestAssured.post("/api/customers", request);
    }

    public static CustomerRequest createRequest(String name, String email) {
        name = name == null ? DEFAULT_NAME : name;
        email = email == null ? DEFAULT_EMAIL : email;
        return new CustomerRequest(
            name,
            "password12!@",
            email,
            "some-address",
            "010-0000-0001"
        );
    }
}
