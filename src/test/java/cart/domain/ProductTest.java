package cart.domain;

import cart.fixture.ImageFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Test
    void 상품명이_50자를_초과하면_예외가_발생한다() {
        String name = "a".repeat(51);

        Assertions.assertThatThrownBy(() -> new Product(name, ImageFixture.url, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 50자를 초과할 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void 상품명이_빈_값이면_예외가_발생한다(String name) {
        Assertions.assertThatThrownBy(() -> new Product(name, ImageFixture.url, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 비어있을 수 없습니다.");
    }
}
