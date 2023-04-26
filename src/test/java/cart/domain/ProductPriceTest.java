package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @Test
    @DisplayName("가격이 음수라면 예외를 발생시킨다")
    void test_productPrice_exception(){
        //given, when, then
        Assertions.assertThatThrownBy(() -> new ProductPrice(-10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10000})
    @DisplayName("가격이 0 혹은 양수여야한다")
    void test__productPrice(int price){
        //given, when, then
        Assertions.assertThatCode(() -> new ProductPrice(price))
                .doesNotThrowAnyException();
    }
}
