package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;

public class CustomerFixture {

    public static ExtractableResponse<Response> signUp(final String userId, final String nickname,
                                                       final String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("nickname", nickname);
        requestBody.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/signUp")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> login(final String userId, final String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", userId);
        requestBody.put("password", password);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/login")
                .then().log().all()
                .extract();
    }
}
