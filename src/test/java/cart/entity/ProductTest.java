package cart.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import cart.exception.DomainException;

class ProductTest {

    @Test
    @DisplayName("상품 엔티티를 생성한다.")
    void product() {
        assertDoesNotThrow(() -> Product.of(1L, "a", "http://www.naver.com", 3000));
    }

    @ParameterizedTest
    @MethodSource("makeProduct")
    @DisplayName("잘못된 값을 검증한다")
    void invalidProductTest(String name, String imgUrl, int price) {
        assertThatThrownBy(() -> Product.of(null, name, imgUrl, price))
            .isInstanceOf(DomainException.class);

    }

    static Stream<Arguments> makeProduct() {
        return Stream.of(Arguments.of("a".repeat(256), "https://naver.com", 1000),
            Arguments.of("aaa", "https://naver" + "a".repeat(8001) + ".com", 1000),
            Arguments.of("aaa", "https://naver.com", -1000));
    }
}
