package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.ProductFixture.CHEESE;
import static woowacourse.shoppingcart.ProductFixture.PAPER;
import static woowacourse.shoppingcart.ProductFixture.PEN;
import static woowacourse.shoppingcart.ProductFixture.WATER;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.Product;

public class ProductAcceptanceTest extends AcceptanceTest {

    public static Stream<Arguments> provideFindProducts() {
        return Stream.of(
                Arguments.of(2, 2, List.of(PAPER, PEN)),
                Arguments.of(4, 2, List.of()),
                Arguments.of(3, 2, List.of(PEN))
        );
    }

    public static Stream<Arguments> provideFindProduct() {
        return Stream.of(
                Arguments.of(1L, WATER),
                Arguments.of(2L, CHEESE),
                Arguments.of(3L, PAPER),
                Arguments.of(4L, PEN)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFindProduct")
    void 상품_조회(Long productId, Product expected) {
        var requestPath = String.format("/products/%d", productId);

        var actual = RestAssured.given().log().all()
                .get(requestPath)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(Product.class);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideFindProducts")
    void 페이지별_상품_목록_조회(int size, int page, List<Product> expected) {
        var requestPath = String.format("/products?size=%d&page=%d", size, page);

        var response = RestAssured.given().log().all()
                .get(requestPath)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        var products = response.body().jsonPath().getList("products", Product.class);

        assertThat(products).containsAll(expected);
    }

    @Test
    void 페이지별_상품_목록_조회_size_와_page_가_없는_경우() {
        var response = RestAssured.given().log().all()
                .get("/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        var products = response.body().jsonPath().getList("products", Product.class);

        assertThat(products).contains(WATER, CHEESE, PAPER, PEN);
    }
}
