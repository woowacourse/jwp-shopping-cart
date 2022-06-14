package woowacourse.shoppingcart.acceptance;

import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dto.ProductsResponse;
import java.util.List;

@DisplayName("상품 관련 기능")
public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "치킨";
    private static final int PRICE = 10_000;
    private static final String IMAGE_URL = "http://example.com/chicken.jpg";

    @DisplayName("상품을 추가한다")
    @Test
    void addProduct() {
        // given, when
        final ExtractableResponse<Response> response =
                requestPostWithBody("/api/products", new ProductSaveRequest(NAME, PRICE, IMAGE_URL));

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("상품을 조회한다")
    @Test
    void getProduct() {
        // given
        requestPostWithBody("/api/products", new ProductSaveRequest(NAME, PRICE, IMAGE_URL));

        // when
        final ExtractableResponse<Response> response = requestGet("/api/products/" + 1);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(ProductResponse.class)).usingRecursiveComparison()
                        .isEqualTo(new ProductResponse(1L, NAME, PRICE, IMAGE_URL))
        );
    }

    @DisplayName("상품 목록을 조회한다")
    @Test
    void getProducts() {
        // given
        final String name = "맥주";
        final int price = 20_000;
        final String imageUrl = "http://example.com/beer.jpg";

        requestPostWithBody("/api/products", new ProductSaveRequest(NAME, PRICE, IMAGE_URL));
        requestPostWithBody("/api/products", new ProductSaveRequest(name, price, imageUrl));

        // when
        final ExtractableResponse<Response> response = requestGet("/api/products");
        final ProductsResponse productResponses = response.jsonPath().getObject(".", ProductsResponse.class);
        final ProductsResponse responses = ProductsResponse.from(List.of(
                new ProductResponse(1L, NAME, PRICE, IMAGE_URL),
                new ProductResponse(2L, name, price, imageUrl)
        ));
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(productResponses).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(responses));
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        // given
        final ExtractableResponse<Response> createResponse =
                requestPostWithBody("/api/products", new ProductSaveRequest(NAME, PRICE, IMAGE_URL));
        final long productId = parseLong(createResponse.header("Location").split("/products/")[1]);

        // when
        final ExtractableResponse<Response> response = requestDelete("/api/products/" + productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 상품_등록_요청(String name, int price, String imageUrl) {
        Product productRequest = new Product(name, price, imageUrl);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when().post("/api/products")
                .then().log().all()
                .extract();
    }

    public static Long 상품_등록되어_있음(String name, int price, String imageUrl) {
        ExtractableResponse<Response> response = 상품_등록_요청(name, price, imageUrl);
        return Long.parseLong(response.header("Location").split("/products/")[1]);
    }
}
