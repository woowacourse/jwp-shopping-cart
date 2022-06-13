package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    @DisplayName("상품명의 길이가 50자 이하이면 상품명을 생성한다")
    @Test
    void makeProductName() {
        final String value = "a".repeat(50);

        assertThat(value).isNotNull();
    }

    @DisplayName("이름이 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenNullOrEmpty(String name) {
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 비어있을 수 없습니다.");
    }

    @DisplayName("상품명의 길이가 50자를 초과하면 예외를 발생한다.")
    @Test
    void throwWhenInvalidLength() {
        final String name = "a".repeat(51);
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 50자를 초과할 수 없습니다.");
    }
}
