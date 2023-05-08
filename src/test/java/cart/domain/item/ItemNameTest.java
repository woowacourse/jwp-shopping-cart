package cart.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.item.ItemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemNameTest {

    @Test
    @DisplayName("ItemName이 생성된다.")
    void createItemNameSuccess() {
        final ItemName itemName = assertDoesNotThrow(() -> new ItemName("맥북"));

        assertThat(itemName).isExactlyInstanceOf(ItemName.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "dfakdfkafjasdjdflsjfkadjl2aenfnfanelkfnafnsftftafed"})
    @DisplayName("name의 길이가 1이상 50이하가 아니면 예외가 발생한다.")
    void createItemNameFailWithWrongName(String name) {
        assertThatThrownBy(() -> new ItemName(name))
                .isInstanceOf(ItemException.class)
                .hasMessage("상품의 이름은 최소 1자, 최대 50자까지 입력할 수 있습니다.");
    }
}
