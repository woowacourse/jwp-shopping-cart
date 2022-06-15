package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.application.ProductFixture.바나나_요청;
import static woowacourse.shoppingcart.application.ProductFixture.사과_요청;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductResponse;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {

        ExtractableResponse<Response> response = post("/products", 바나나_요청);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotEmpty()
        );

    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        post("/products", 바나나_요청);
        post("/products", 사과_요청);

        ExtractableResponse<Response> response = get("/products");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<ProductResponse> products = response.jsonPath().getList("products", ProductResponse.class);
        assertAll(
                () -> assertThat(products.size()).isEqualTo(2),
                () -> {
                    ProductResponse product = products.get(0);
                    assertAll(
                            () -> assertThat(product.getId()).isEqualTo(1),
                            () -> assertThat(product.getName()).isEqualTo("바나나"),
                            () -> assertThat(product.getPrice()).isEqualTo(1_000),
                            () -> assertThat(product.getImageUrl()).isEqualTo("banana.com")
                    );
                }
        );
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        post("/products", 바나나_요청);

        ExtractableResponse<Response> response = get("/products/" + 1);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isEqualTo(1L),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo("바나나"),
                () -> assertThat(response.jsonPath().getInt("price")).isEqualTo(1_000),
                () -> assertThat(response.jsonPath().getString("imageUrl")).isEqualTo("banana.com")
        );
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        post("/products", 바나나_요청);

        ExtractableResponse<Response> response = delete("/products/" + 1);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(new Name(name), new Price(price), new ImageUrl(imageUrl));

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/products")
                .then().log().all()
                .extract();
    }
}
