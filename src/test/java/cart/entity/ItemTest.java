package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @DisplayName("id 값을 기준으로 객체가 동일한지 판단한다.")
    @Test
    void equals_true() {
        Item item1 = new Item(1L, "안녕", "test", 1000);
        Item item2 = new Item(1L, "반가워", "2", 2000);

        assertThat(item1.equals(item2)).isTrue();
    }

    @DisplayName("id 값이 다르다면 객체가 다르다고 판단한다.")
    @Test
    void equals_false() {
        Item item1 = new Item(1L, "안녕", "test", 1000);
        Item item2 = new Item(2L, "안녕", "test", 1000);

        assertThat(item1.equals(item2)).isFalse();
    }
}
