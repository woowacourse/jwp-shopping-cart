package cart.entity.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest(name = "가격이 {0}일 떄")
    @NullSource
    @DisplayName("가격이 존재하지 않을 경우 오류를 던진다.")
    void priceNotExist(final Integer value) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 0보다 작을 경우 오류를 던진다")
    void priceUnderMinPrice() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격을 조회한다.")
    void getValue() {
        //given
        final Price price = new Price(1000);

        //when
        //then
        assertThat(price.getValue()).isEqualTo(1000);
    }
}
