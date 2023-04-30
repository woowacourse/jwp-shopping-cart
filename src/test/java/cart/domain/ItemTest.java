package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.NameRangeException;
import cart.exception.PriceRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemTest {

    @Test
    @DisplayName("상품이 생성된다.")
    void createItemSuccess() {
        Item item = new Item("맥북", "http:image.url", 1_500_000);

        assertThat(item).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "dfakdfkafjasdjdflsjfkadjl2aenfnfanelkfnafnsftftafed"})
    @DisplayName("상품 이름의 길이가 1이상 50이하가 아니면 예외가 발생한다.")
    void createItemFailWithWrongName(String name) {
        assertThatThrownBy(() -> new Item(name, "http:image.url", 1_500_000))
                .isInstanceOf(NameRangeException.class)
                .hasMessage("상품의 이름은 최소 1자, 최대 50자까지 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 100_000_001})
    @DisplayName("상품 가격이 10원 이상 1억 이하가 아니면 예외가 발생한다.")
    void createItemFailWithWrongPrice(int price) {
        assertThatThrownBy(() -> new Item("맥북", "http:image.url", price))
                .isInstanceOf(PriceRangeException.class)
                .hasMessage("상품의 금액은 최소 10원, 최대 1억원 까지 가능합니다.");
    }
}
