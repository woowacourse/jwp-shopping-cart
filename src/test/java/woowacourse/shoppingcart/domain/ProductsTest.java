package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.ProductFixture.CHEESE;
import static woowacourse.shoppingcart.ProductFixture.PAPER;
import static woowacourse.shoppingcart.ProductFixture.PEN;
import static woowacourse.shoppingcart.ProductFixture.WATER;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProductsTest {

    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(1, 1, List.of(WATER)),
                Arguments.of(2, 2, List.of(PAPER, PEN)),
                Arguments.of(3, 2, List.of(PEN)),
                Arguments.of(4, 2, List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    void 상품_갯수_페이지로_상품_목록_자르기(final int size, final int page, final List<Product> expected) {
        final var productList = List.of(WATER, CHEESE, PAPER, PEN);
        final var products = new Products(productList);

        final var slicedProducts = products.slice(size, page);

        assertThat(slicedProducts).isEqualTo(expected);
    }
}
