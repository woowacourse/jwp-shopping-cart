package woowacourse.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

public class RestAssuredConvenienceMethod {

    public static ValidatableResponse postRequest(Object body, String path) {
        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all();
    }
}
