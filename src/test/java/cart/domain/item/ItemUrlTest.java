package cart.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.item.ItemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemUrlTest {

    @Test
    @DisplayName("ItemUrl이 생성된다.")
    void createItemUrlSuccess() {
        final ItemUrl itemUrl = assertDoesNotThrow(() -> new ItemUrl("http://image.com"));

        assertThat(itemUrl).isExactlyInstanceOf(ItemUrl.class);
    }

    @Test
    @DisplayName("url이 http이라는 접두사를 가지고 있지 않으면 예외가 발생한다.")
    void createItemUrlFailWithHasNotHttpPrefix() {
        assertThatThrownBy(() -> new ItemUrl("hello.world"))
                .isInstanceOf(ItemException.class)
                .hasMessage("이미지 링크는 HTTP, HTTPS 형식으로만 입력할 수 있습니다.");
    }
}
