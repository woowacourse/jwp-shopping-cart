package cart.domain;

import cart.domain.product.Name;
import cart.exception.NameCreateFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NameTest {

    @Test
    @DisplayName("이름을 정상적으로 생성한다.")
    void create_name_success() {
        // given
        String name = "name";

        // when & then
        assertDoesNotThrow(() -> new Name(name));
    }

    @Test
    @DisplayName("이름은 공백이면 안된다.")
    void throws_exception_when_name_is_blank() {
        // given
        String name = "";

        // when & then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(NameCreateFailException.class);
    }

    @Test
    @DisplayName("이름을 수정한다.")
    void edit_success() {
        // given
        Name name = new Name("name");
        String expected = "change";

        // when
        name.edit(expected);

        // then
        assertThat(name.getName()).isEqualTo(expected);
    }

    @Test
    @DisplayName("이름 수정에 실패한다.")
    void edit_fail_when_invalid_name() {
        // given
        Name name = new Name("name");
        String expected = "";

        // when & then
        assertThatThrownBy(() -> name.edit(expected))
                .isInstanceOf(NameCreateFailException.class);
    }
}
