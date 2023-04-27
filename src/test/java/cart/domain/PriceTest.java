package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceTest {

    @DisplayName("가격을 입력받아 정상 생성한다")
    @Test
    void create() {
        //when
        int value = 50000;
        Price price = new Price(value);
        //then
        assertThat(price.getValue()).isEqualTo(value);
    }

    @DisplayName("가격에 빈값을 입력하면 예외를 반환한다.")
    @Test
    void createExceptionWithNull() {
        //then
        assertThatThrownBy(() -> new Price(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격에 음수값을 입력하면 예외를 반환한다.")
    @Test
    void createExceptionWithNegative() {
        //then
        assertThatThrownBy(() -> new Price(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격에 10억 이상 값을 입력하면 예외를 반환한다.")
    @Test
    void createExceptionWithOverOneBillion() {
        //then
        assertThatThrownBy(() -> new Price(1_000_000_001)).isInstanceOf(IllegalArgumentException.class);
    }
}
