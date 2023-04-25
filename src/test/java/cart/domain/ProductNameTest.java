package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
