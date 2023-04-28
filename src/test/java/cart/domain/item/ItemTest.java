package cart.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    @DisplayName("상품이 생성된다.")
    void createItemSuccess() {
        Item item = new Item("맥북", "http:image.url", 1_500_000);

        assertThat(item).isExactlyInstanceOf(Item.class);
    }
}
