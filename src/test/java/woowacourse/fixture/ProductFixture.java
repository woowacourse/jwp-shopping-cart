package woowacourse.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;

public class ProductFixture {

    public static Long getProductId(final String name, final int price, final String imageUrl) {
        ExtractableResponse<Response> response = addProduct(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static ExtractableResponse<Response> addProduct(final String name, final int price, final String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> findProductById(final Long id) {
        return RestAssured
                .given().log().all()
                .when().get("/products/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> findProductsInPage(final Long pageNum, final Long limitCount) {
        return RestAssured
                .given().log().all()
                .when().get("/products?page=" + pageNum + "&limit=" + limitCount)
                .then().log().all()
                .extract();
    }
}
