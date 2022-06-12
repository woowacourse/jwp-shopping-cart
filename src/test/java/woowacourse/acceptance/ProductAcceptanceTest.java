package woowacourse.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import woowacourse.setup.AcceptanceTest;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductDto;
import woowacourse.shoppingcart.dto.response.ProductsDto;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("상품 관련 기능")
class ProductAcceptanceTest extends AcceptanceTest {

    private static final Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
    private static final Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
    private static final Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");

    @Autowired
    private DatabaseFixture databaseFixture;

    @DisplayName("GET /products - 모든 상품 조회 (로그인 불필요)")
    @Nested
    class GetProductsTest {

        @Test
        void 마지막으로_추가된_상품들부터_내림차순으로_정렬하여_조회_200() {
            databaseFixture.save(호박, 고구마, 호박고구마);
            ExtractableResponse<Response> response = 상품_목록_조회_요청();
            int 상태코드 = response.statusCode();
            ProductsDto actualResponseBody = response.as(ProductsDto.class);
            ProductsDto expectedResponseBody = new ProductsDto(List.of(호박고구마, 고구마, 호박));

            assertThat(상태코드).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        private ExtractableResponse<Response> 상품_목록_조회_요청() {
            return RestAssured.given().log().all()
                    .when().get("/products")
                    .then().log().all()
                    .extract();
        }
    }

    @DisplayName("GET /products/{productId} - 개별 상품 조회 (로그인 불필요)")
    @Nested
    class GetProductTest {

        @Test
        void productId에_해당되는_상품이_존재하는_경우_200() {
            databaseFixture.save(호박);
            ExtractableResponse<Response> response = 상품_조회_요청(1L);
            int 상태코드 = response.statusCode();
            ProductDto actualResponseBody = response.as(ProductDto.class);
            ProductDto expectedResponseBody = new ProductDto(호박);

            assertThat(상태코드).isEqualTo(HttpStatus.OK.value());
            assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
        }

        @Test
        void productId에_해당되는_상품이_존재하지_않는_경우_404() {
            ExtractableResponse<Response> response = 상품_조회_요청(9999L);
            int 상태코드 = response.statusCode();

            assertThat(상태코드).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        private ExtractableResponse<Response> 상품_조회_요청(Long productId) {
            return RestAssured.given().log().all()
                    .when().get("/products/" + productId)
                    .then().log().all()
                    .extract();
        }
    }
}
