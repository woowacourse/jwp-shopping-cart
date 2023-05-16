package cart.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import cart.exception.DomainException;

class CartItemTest {
    @Test
    @DisplayName("장바구니 엔티티를 생성한다.")
    void product() {
        assertDoesNotThrow(() -> CartItem.of(1L, 1L, 1L));
    }

    @ParameterizedTest
    @MethodSource("makeCart")
    @DisplayName("잘못된 값을 검증한다")
    void invalidProductTest(Long id, Long memberId, Long productId) {
        assertThatThrownBy(() -> CartItem.of(id, memberId, productId))
            .isInstanceOf(DomainException.class);

    }

    static Stream<Arguments> makeCart() {
        return Stream.of(
            Arguments.of(1L, -1L, 1L),
            Arguments.of(1L, 1L, -1L));
    }
}
