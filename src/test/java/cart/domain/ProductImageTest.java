package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductImageTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "judi"})
    @DisplayName("http, https로 시작하지 않는 경우 예외를 발생시킨다")
    void test_product_image_prefix_exception(String url) {
        // given, when, then
        assertThatThrownBy(() -> new ProductImage(url))
                .isInstanceOf(MalformedURLException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://", "https://"})
    @DisplayName("http, https로 시작하는 경우 예외를 발생시키지 않는다")
    void test_product_image_prefix(String url) {
        // given, when, then
        assertThatCode(() -> new ProductImage(url))
                .doesNotThrowAnyException();
    }
}
