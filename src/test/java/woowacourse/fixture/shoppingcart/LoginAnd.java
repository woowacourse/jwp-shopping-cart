package woowacourse.fixture.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.CustomerChangePasswordRequest;
import woowacourse.shoppingcart.ui.dto.CustomerChangeRequest;

public class LoginAnd {

    private final TCustomer tcustomer;

    public LoginAnd(TCustomer tcustomer) {
        tcustomer.signIn();
        this.tcustomer = tcustomer;
    }

    public CustomerResponse showMyInfo() {
        ExtractableResponse<Response> response = get("/api/customers/me", tcustomer.getAccessToken());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return response.as(CustomerResponse.class);
    }

    public ExtractableResponse<Response> get(String url, Object params) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + params)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> changeNickname(String nickname) {
        return patch(
                "/api/customers",
                List.of(tcustomer.getAccessToken(), new CustomerChangeRequest(nickname)));
    }

    public ExtractableResponse changePassword(String prevPassword, String newPassword) {
        return patch(
                "/api/customers/password",
                List.of(tcustomer.getAccessToken(), new CustomerChangePasswordRequest(prevPassword, newPassword)));
    }

    private ExtractableResponse<Response> patch(String url, List<Object> params) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + params.get(0))
                .when()
                .body(params.get(1))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch(url)
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> deleteCustomer() {
        return delete("/api/customers", tcustomer.getAccessToken());
    }

    private ExtractableResponse<Response> delete(String url, Object params) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + params)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }
}
