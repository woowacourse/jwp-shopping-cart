package woowacourse.shoppingcart.acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.QuantityRequest;

public class CartSimpleAssured {

    public static ExtractableResponse<Response> 장바구니_상품_등록(String token, String productId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .body(Map.of("productId", productId))
                .post("/users/me/carts")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 장바구니_상품_조회(String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .get("/users/me/carts")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 장바구니_상품_삭제(String token, Long productId) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .pathParam("productId", productId)
                .when().log().all()
                .delete("/users/me/carts/{productId}")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 장바구니_상품_수량_수정(String token, String productId, int quantity) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .pathParam("productId", productId)
                .when().log().all()
                .body(new QuantityRequest(quantity))
                .put("/users/me/carts/{productId}")
                .then().log().all().extract();
    }
}
