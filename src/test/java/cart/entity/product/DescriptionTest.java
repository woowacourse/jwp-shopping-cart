package cart.entity.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    @DisplayName("상품 설명이 255자 초과일 경우 예외를 던진다.")
    void descriptionOverLength() {
        assertThatThrownBy(() -> new Description("1".repeat(256)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
