package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductTest {

    @DisplayName("상품을 성공적으로 생성한다.")
    @Test
    void constructor() {
        assertThat(new Product("사과", 200, "https://example.com/apple.jpg"))
                .isNotNull();
    }

    @DisplayName("상품명이 공백일 경우 예외를 발생시킨다.")
    @Test
    void constructor_name_blank() {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new Product("", 200, "https://example.com/apple.jpg"))
                .withMessageContaining("상품명");
    }

    @DisplayName("상품명이 255자를 초과할 경우 예외를 발생시킨다.")
    @Test
    void constructor_name_long() {
        final String productName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv" +
                "wxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk" +
                "lmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new Product(productName, 200, "https://example.com/apple.jpg"))
                .withMessageContaining("상품명");
    }
}