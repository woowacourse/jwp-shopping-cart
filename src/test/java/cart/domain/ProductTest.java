package cart.domain;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTest {

    @ParameterizedTest
    @MethodSource("makeProduct")
    @DisplayName("잘못된 값을 검증한다")
    void invalidProductTest(String name, String imgUrl, int price) {
        Assertions.assertThatThrownBy(() -> Product.createWithoutId(name, imgUrl, price))
            .isInstanceOf(IllegalArgumentException.class);

    }

    static Stream<Arguments> makeProduct() {
        return Stream.of(Arguments.of("a".repeat(256), "https://naver.com", 1000),
            Arguments.of("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000),
            Arguments.of("aaa", "https://naver.com", -1000));
    }
}
