package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;

public class AuthFixture {


    public static ExtractableResponse<Response> findById(final String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> withdraw(final String token, final String password) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", password);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().delete("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> update(final String token, final String nickname) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nickname", nickname);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> updatePassword(final String token, final String oldPassword,
                                                               final String newPassword) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("oldPassword", oldPassword);
        requestBody.put("newPassword", newPassword);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/auth/customers/profile/password")
                .then().log().all()
                .extract();
    }
}
