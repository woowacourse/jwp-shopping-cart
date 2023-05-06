package cart.entity.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @ParameterizedTest(name = "상품명이 {0}일 떄")
    @NullAndEmptySource
    @DisplayName("상품명이 존재하지 않을 경우 예외를 던진다.")
    void nameNotExist(final String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명이 50자 초과일 경우 예외를 던진다.")
    void nameOverLength() {
        assertThatThrownBy(() -> new Name("1".repeat(51)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
