package cart.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.ImageUrlExtensionNotValidException;
import cart.exception.NegativePriceException;
import cart.exception.ProductNameLengthOverException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Nested
    @DisplayName("product 생성자 테스트")
    class initializeTest {

        @ParameterizedTest
        @DisplayName("이름의 유효성 검증")
        @ValueSource(ints = {0, 256})
        void validateNameTest(int length) {
            String name = ".".repeat(length);
            assertThatThrownBy(() -> new Product(name, "www.google.co.kr.png", 4000))
                    .isInstanceOf(ProductNameLengthOverException.class);
        }

        @Test
        @DisplayName("가격의 유효성 검증")
        void validatePrice() {
            int price = -100;
            assertThatThrownBy(() -> new Product("상품", "www.google.co.kr.png", price))
                    .isInstanceOf(NegativePriceException.class);
        }

        @ParameterizedTest
        @DisplayName("이미지 url 유효성 검증")
        @ValueSource(strings = {"www.google.co.kr.mp4", "www.google.co.kr.exe"})
        void validateImageUrl(String url) {
            assertThatThrownBy(() -> new Product("상품", url, 4000))
                    .isInstanceOf(ImageUrlExtensionNotValidException.class);
        }
    }
}
