package woowacourse.shoppingcart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemPropertyException;

public class CartItemTest {

    @ParameterizedTest
    @DisplayName("name 이 null 인 경우 예외를 던진다.")
    @NullSource
    void InvalidName_null(String name) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new CartItem(1L, 1L, name, 10000, "naver.com", 5, 5))
            .isInstanceOf(InvalidCartItemPropertyException.class);
    }

    @ParameterizedTest
    @DisplayName("길이가 잘못된 name 인 경우 예외를 던진다.")
    @CsvSource(value = {"a:0", "a:101"}, delimiter = ':')
    void InvalidName_length(String name, int count) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new CartItem(1L, 1L, name.repeat(count), 10000, "naver.com", 5, 5))
            .isInstanceOf(InvalidCartItemPropertyException.class);
    }

    @Test
    @DisplayName("price 가 음수인 경우 예외를 던진다.")
    void InvalidPrice() {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new CartItem(1L, 1L, "name", -1, "naver.com", 5, 5))
            .isInstanceOf(InvalidCartItemPropertyException.class);
    }

    @ParameterizedTest
    @DisplayName("quantity 나 count 가 음수인 경우 예외를 던진다.")
    @CsvSource(value = {"-1:0", "0:-1"}, delimiter = ':')
    void InvalidAmount(int quantity, int count) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new CartItem(1L, 1L, "name", 0, "naver.com", quantity, count))
            .isInstanceOf(InvalidCartItemPropertyException.class);
    }
}
