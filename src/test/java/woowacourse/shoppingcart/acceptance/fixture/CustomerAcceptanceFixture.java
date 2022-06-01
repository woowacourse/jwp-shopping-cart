package woowacourse.shoppingcart.acceptance.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.support.SimpleRestAssured;

public class CustomerAcceptanceFixture {

    public static ExtractableResponse<Response> saveCustomer() {
        CustomerRequest request = new CustomerRequest(
                "username",
                "password12!@",
                "example@example.com",
                "some-address",
                "010-0000-0001"
        );
        return SimpleRestAssured.post("/api/customers", request);
    }
}
