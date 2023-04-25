package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductImageUrlTest {

    @DisplayName("상품 이미지 url이 null이면 예외를 반환한다.")
    @Test
    void shouldThrowExceptionWhenUrlIsNull() {
        assertThatThrownBy(() -> new ProductImageUrl(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품 이미지 url이 null입니다.");
    }

    @DisplayName("상품 이미지 url이 공백이면 예외를 반환한다.")
    @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
    @ValueSource(strings = {" "})
    @EmptySource
    void shouldThrowExceptionWhenUrlIsBlank(String inputUrl) {
        assertThatThrownBy(() -> new ProductImageUrl(inputUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품 이미지 url이 비어있습니다.");
    }

}
