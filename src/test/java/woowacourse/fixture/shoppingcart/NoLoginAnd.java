package woowacourse.fixture.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.global.exception.ErrorResponse;
import woowacourse.shoppingcart.ui.dto.CustomerChangePasswordRequest;
import woowacourse.shoppingcart.ui.dto.CustomerChangeRequest;

public class NoLoginAnd {

    private TCustomer tCustomer;

    public NoLoginAnd(TCustomer tCustomer) {
        this.tCustomer = tCustomer;
    }

    public ErrorResponse showMyInfo() {
        ExtractableResponse<Response> response = get("/api/customers/me");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    public ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public ErrorResponse changeNickname(String nickname) {
        ExtractableResponse<Response> response = patch("/api/customers", new CustomerChangeRequest(nickname));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    private ExtractableResponse<Response> patch(String url, Object params) {
        return RestAssured.given().log().all()
                .when()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch(url)
                .then().log().all()
                .extract();
    }

    public ErrorResponse changePassword(String prevPassword, String newPassword) {
        ExtractableResponse<Response> response = patch("/api/customers/password", new CustomerChangePasswordRequest(prevPassword, newPassword));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    public ErrorResponse deleteCustomer() {
        ExtractableResponse<Response> response = delete("/api/customers");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    public ExtractableResponse<Response> delete(String url) {
        return RestAssured.given().log().all()
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
