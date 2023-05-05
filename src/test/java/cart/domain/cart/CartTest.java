package cart.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

@DisplayName("장바구니는 ")
class CartTest {

    @ParameterizedTest
    @CsvSource({"1, 10, true", "1, 15, false", "2, 10, false"})
    @DisplayName("상품이 이미 담겨있는지 확인할 수 있다.")
    void containsTest(Long memberId, Long productId, boolean expected) {
        // given
        Item item1 = new Item(1L, 1L, 10L);
        Item item2 = new Item(2L, 2L, 20L);
        List<Item> items = new ArrayList<>(List.of(item1, item2));
        Cart cart = new Cart(memberId, items);

        // when
        Item requestedItem = new Item(memberId, productId);
        boolean result = cart.contains(requestedItem);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
