package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @DisplayName("상품을 생성할 때")
    @Nested
    class Validate {

        @DisplayName("상품명 길이가 1이상 20이하가")
        @Nested
        class NameLength {
            @ValueSource(strings = {"", " ", "012345678901234567890"})
            @ParameterizedTest(name = "아닐 경우 예외가 발생한다. [상품명 : {0}]")
            void greater(final String name) {
                assertThatThrownBy(() -> new Product(ProductId.from(1L), name, 300, "이미지-url"))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(strings = {
                    "0", "01", "012", "0123", "01234",
                    "0123456789012345", "01234567890123456", "012345678901234567", "0123456789012345678", "01234567890123456789"
            })
            @ParameterizedTest(name = "맞을 경우 생성 성공한다. [상품명 : {0}]")
            void ok(final String name) {
                assertDoesNotThrow(() -> new Product(ProductId.from(1L), name, 300, "이미지-url"));
            }
        }

        @DisplayName("상품 가격이 0원 미만이")
        @Nested
        class PriceRange {
            @ValueSource(ints = {-1, -2, -3, -1000000000})
            @ParameterizedTest(name = "맞을 경우에 예외가 발생한다. [상품 가격 : {0}]")
            void negative(final int price) {
                assertThatThrownBy(() -> new Product(ProductId.from(1L), "헤나", price, "이미지-url"))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(ints = {0, 1, 2, 1000000000})
            @ParameterizedTest(name = "아닐 경우에 생성 성공한다. [상품 가격 : {0}]")
            void positiveOrZero(final int price) {
                assertDoesNotThrow(() -> new Product(ProductId.from(1L), "헤나", price, "이미지-url"));
            }
        }

        @DisplayName("상품 사진의 url이")
        @Nested
        class Image {
            @DisplayName("null일 경우 예외가 발생한다.")
            @Test
            void isNull() {
                assertThatThrownBy(() -> new Product(ProductId.from(1L), "헤나", 10000, null))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(strings = {"0", "01", "url", "http://"})
            @ParameterizedTest(name = "문자열일 경우 생성 성공한다. [url : {0}]")
            void isString(final String imageUrl) {
                assertDoesNotThrow(() -> new Product(ProductId.from(1L), "헤나", 10000, imageUrl));
            }
        }
    }
}
