package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NameTest {

    @Test
    @DisplayName("")
    void create_name_success() {
        // given
        String name = "name";

        // when & then
        assertDoesNotThrow(() -> new Name(name));
    }

    @Test
    @DisplayName("")
    void throws_exception_when_name_is_blank() {
        // given
        String name = "";

        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
