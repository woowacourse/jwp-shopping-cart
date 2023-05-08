package cart.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.item.ItemException;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemPriceTest {

    @Test
    @DisplayName("ItemPrice가 생성된다.")
    void createItemPriceSuccess() {
        ItemPrice itemPrice = assertDoesNotThrow(() -> new ItemPrice(BigInteger.TEN));

        assertThat(itemPrice).isExactlyInstanceOf(ItemPrice.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 100_000_001})
    @DisplayName("price가 10원 이상 1억 이하가 아니면 예외가 발생한다.")
    void createItemFailWithWrongPrice(int price) {
        assertThatThrownBy(() -> new ItemPrice(BigInteger.valueOf(price)))
                .isInstanceOf(ItemException.class)
                .hasMessage("상품의 금액은 최소 10원, 최대 1억원까지 입력할 수 있습니다.");
    }
}
