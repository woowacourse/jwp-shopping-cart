package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.shoppingcart.dto.request.CreateOrderDetailRequest;
import woowacourse.shoppingcart.dto.request.CreateProductRequest;
import woowacourse.shoppingcart.dto.request.EditCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;

public class RequestFixture {

    public static ExtractableResponse<Response> 회원_가입_요청(String name, String password) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SignUpRequest(name, password))
                .when().post("/api/customers")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인(String name, String password) {
        return RestAssured
                .given().log().all()
                .body(new LoginRequest(name, password))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all().extract();
    }

    public static String 로그인_및_토큰_발급(String name, String password) {
        return 로그인(name, password).as(LoginResponse.class).getAccessToken();
    }

    public static ExtractableResponse<Response> 내_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_수정_요청(String accessToken, String name, String password) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new EditCustomerRequest(name, password))
                .when().put("/api/customers/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_이름_중복_검사_요청(String userName) {
        return RestAssured
                .given().log().all()
                .when().get("/api/customers/exists?userName=" + userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        CreateProductRequest request = new CreateProductRequest(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static ExtractableResponse<Response> 로그인하지_않고_상품_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_후_상품_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인하지_않고_상품_조회_요청(Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인_후_상품_조회_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
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

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/customers/me/carts/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수량_변경_요청(String accessToken, Long cartId, int quantity) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("quantity", quantity);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().patch("/api/customers/me/carts/" + cartId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문하기_요청(List<CreateOrderDetailRequest> orderRequests,
                                                        String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static Long 주문하기_요청_성공되어_있음(List<CreateOrderDetailRequest> orderRequests, String accessToken) {
        ExtractableResponse<Response> response = 주문하기_요청(orderRequests, accessToken);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/orders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(Long orderId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/me/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }
}
