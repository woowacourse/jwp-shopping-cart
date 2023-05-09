package cart.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static cart.vo.Price.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @DisplayName("올바르지 않은 가격이 들어왔을 때 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1_000_000_001})
    void createPriceFail(int input) {
        assertThatThrownBy(() -> Price.from(input))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("올바르지 않은 가격입니다.");
    }

    @DisplayName("올바른 가격이 들어왔을 때 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 1_000_000_000})
    void createPriceSuccess(int input) {
        Price price = from(input);

        assertThat(price.getValue()).isEqualTo(input);
    }

}
