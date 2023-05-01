package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @DisplayName("상품명이 1글자 미만이거나 50글자 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefghijklmnopqrxtuvwxyzabcdefghijklmnopqrxtuvwxy"})
    void exceptionWhenProductNameOutOfRange(String name){
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명은 1글자 이상, 50글자 이하여야합니다.");
    }



}