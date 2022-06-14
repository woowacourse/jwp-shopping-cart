package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;
import woowacourse.shoppingcart.dto.product.ProductCreateRequest;

public class RequestFixture {

    public static ExtractableResponse<Response> 로그인_요청(TokenRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();
    }

    public static String 로그인_요청_및_토큰발급(TokenRequest request) {
        ExtractableResponse<Response> loginResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();

        TokenResponse tokenResponse = loginResponse.body().as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }

    public static ExtractableResponse<Response> 회원가입_요청(CustomerCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    public static long 회원가입_요청_및_ID_추출(CustomerCreateRequest request) {
        return ID_추출(회원가입_요청(request));
    }

    public static ExtractableResponse<Response> 회원조회_요청(String token, Long id) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .when().get("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원정보수정_요청(String token, long id, CustomerUpdateRequest request) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴_요청(String token, long id, PasswordRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/customers/" + id)
                .then().log().all()
                .extract();
    }

    public static long ID_추출(ExtractableResponse<Response> response) {
        String[] locations = response.header("Location").split("/");
        return Long.parseLong(locations[locations.length - 1]);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl, int quantity) {
        ProductCreateRequest productRequest = new ProductCreateRequest(name, price, imageUrl, quantity);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl, int quantity) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl, quantity);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }
}
