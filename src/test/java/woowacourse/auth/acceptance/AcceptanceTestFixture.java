package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.dto.SignupRequest;

public class AcceptanceTestFixture {

    public static final SignupRequest 에덴 = new SignupRequest("leo0842", "eden", "password", "address", new PhoneNumber("010", "1234", "5678"));
    public static final SignupRequest 코린 = new SignupRequest("hamcheeseburger", "corinne", "password", "address", new PhoneNumber("010", "1234", "5678"));

}
