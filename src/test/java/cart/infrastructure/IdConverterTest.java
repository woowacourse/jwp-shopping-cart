package cart.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.dto.common.Id;
import cart.exception.InvalidIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class IdConverterTest {
    private IdConverter converter;

    @BeforeEach
    void setUp() {
        converter = new IdConverter();
    }

    @Test
    @DisplayName("컨버터가 정상적으로 동작해야 한다.")
    void convert_success() {
        // given
        String source = "1";

        // then
        Id id = converter.convert(source);

        // when
        assertThat(id)
                .isNotNull();
        assertThat(id.getId())
                .isEqualTo(1);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("컨버터에 빈 값이 들어오면 예외가 발생해야 한다.")
    void convert_blankSource(String source) {
        // expect
        assertThatThrownBy(() -> converter.convert(source))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("ID는 빈 값이 될 수 없습니다.");
    }

    @Test
    @DisplayName("컨버터에 숫자가 아닌 값이 들어오면 예외가 발생해야 한다.")
    void convert_notANumber() {
        // given
        String source = "a";

        // expect
        assertThatThrownBy(() -> converter.convert(source))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("ID는 숫자만 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1"})
    @DisplayName("컨버터에 0 또는 음수가 들어오면 예외가 발생해야 한다.")
    void convert_notAPositive(String source) {
        // expect
        assertThatThrownBy(() -> converter.convert(source))
                .isInstanceOf(InvalidIdException.class)
                .hasMessage("ID는 양수만 가능합니다.");
    }
}
