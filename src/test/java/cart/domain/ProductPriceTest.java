package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @Test
    @DisplayName("가격이 음수라면 예외를 발생시킨다")
    void test_productPrice_min_price_exception(){
        //given, when, then
        assertThatThrownBy(() -> new ProductPrice(-10_000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10_000,1_000_000})
    @DisplayName("가격이 0 혹은 양수여야한다")
    void test_productPrice(int price){
        //given, when, then
        assertThatCode(() -> new ProductPrice(price))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("가격이 1,000,000 초과라면 예외를 발생시킨다")
    void test_productPrice_max_price_exception(){
        //given,when,then
        assertThatThrownBy(() -> new ProductPrice(1_000_001))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
