package woowacourse.fixture;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.response.ErrorResponse;

public class SimpleResponse {
    private final Response response;

    public SimpleResponse(Response response) {
        this.response = response;
    }

    public ExtractableResponse<Response> extract() {
        return response
                .then().log().all()
                .extract();
    }

    public boolean containsExceptionMessage(String message) {
        return this.toObject(ErrorResponse.class)
                .getMessage()
                .contains(message);
    }

    public <T> T toObject(Class<T> clazz) {
        return this
                .extract()
                .as(clazz);
    }

    public Long getIdFromLocation(String prefix) {
        return Long.parseLong(response.header("Location").split(prefix)[1]);
    }

    public void assertStatus(HttpStatus status) {
        response.then().log().all()
                .statusCode(status.value());
    }

    public void assertHeader(String name, String value) {
        response.then()
                .header(name, equalTo(value));
    }

    public void assertHeader(String name, Matcher<String> matcher) {
        response.then()
                .header(name, matcher);
    }

    public void assertBody(String path, Object value) {
        response.then()
                .body(path, equalTo(value));
    }
}
