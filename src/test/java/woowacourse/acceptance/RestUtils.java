package woowacourse.acceptance;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.customer.CustomerDeleteRequest;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.shoppingcart.dto.QuantityRequest;

public class RestUtils {

	public static ExtractableResponse<Response> signUp(String email, String password, String nickname) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new CustomerRequest(email, password, nickname))
			.when().post("/customers")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> login(String email, String password) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new TokenRequest(email, password))
			.when().post("/auth/login")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> signOut(String token, String password) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().oauth2(token)
			.body(new CustomerDeleteRequest(password))
			.when().delete("/customers")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> update(String token, String nickname, String password,
		String newPassword) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.auth().oauth2(token)
			.body(new CustomerUpdateRequest(nickname, password, newPassword))
			.when().patch("/customers")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> getCustomer(String token) {
		return RestAssured.given().log().all()
			.auth().oauth2(token)
			.when().get("/customers")
			.then().log().all().extract();
	}

	public static ExtractableResponse<Response> addCartItem(String token, Long productId, int quantity) {
		return RestAssured.given().log().all()
			.auth().oauth2(token)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(new QuantityRequest(quantity))
			.when().put("/cart/products/{id}", productId)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> getCartItems(String token) {
		return RestAssured.given().log().all()
			.auth().oauth2(token)
			.when().get("/cart")
			.then().log().all()
			.extract();
	}
}
