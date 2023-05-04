package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    @DisplayName("이름이 빈 칸일 경우 예외가 발생한다.")
    void validateNameNotBlankTest() {
        // given
        String name = "";

        // then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력하세요.");
    }

    @Test
    @DisplayName("이름이 50자 초과일 경우 예외가 발생한다.")
    void validateNameLengthTest() {
        // given
        int nameLength = 51;
        String name = "a".repeat(nameLength);

        // then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 50자 이하여야 합니다. (현재 " + nameLength + "자)");
    }
}