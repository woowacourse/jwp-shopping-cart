package woowacourse.auth.acceptance;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerUpdateRequest;
import woowacourse.auth.dto.TokenRequest;

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

	public static ExtractableResponse<Response> signOut(String token) {
		return RestAssured.given().log().all()
			.auth().oauth2(token)
			.when().delete("/customers")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> update(String token, String nickname, String password, String newPassword) {
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
}
