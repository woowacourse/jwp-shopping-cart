package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.ProductFixture.NOTE;
import static woowacourse.shoppingcart.ProductFixture.WATCH;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.domain.Product;

public class ProductAcceptanceTest extends AcceptanceTest {

    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(2, 2, List.of(NOTE, WATCH)),
                Arguments.of(4, 2, List.of()),
                Arguments.of(3, 2, List.of(WATCH))
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
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
}
