package woowacourse;

import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
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
}
