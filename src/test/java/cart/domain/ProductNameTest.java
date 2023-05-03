package cart.domain;

import cart.business.domain.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    @Test
    @DisplayName("상품 이름의 길이는 10자 이상이면 예외를 발생시킨다.")
    void test_productName_length_exception() {
        //given, when, then
        assertThatThrownBy(() -> new ProductName("judyjudyju"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 이름의 길이는 10자 미만인 경우 예외를 발생시키지 않는다")
    void test_productName_length() {
        //given, when, then
        assertThatCode(() -> new ProductName("judyjudyj"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("상품 이름이 빈 경우에는 예외를 발생시킨다")
    void test_productName_blank_exception(java.lang.String name) {
        //given, when, then
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
