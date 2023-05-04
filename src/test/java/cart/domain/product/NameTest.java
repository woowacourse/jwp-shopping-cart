package cart.domain.product;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class NameTest {

    @ParameterizedTest
    @MethodSource("nameProvider")
    void 상품을_정상적으로_등록할_수_있다(final String input) {
        assertDoesNotThrow(() -> Name.from(input));
    }

    static Stream<Arguments> nameProvider() {
        return Stream.of(
                Arguments.of("a"),
                Arguments.of("b".repeat(255))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" "})
    @NullAndEmptySource
    void 상품명은_공백이나_null_값이_들어오면_예외가_발생한다(final String input) {
        assertThatThrownBy(() -> Name.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품명의_최대_길이를_넘어서는_이름으로_등록하면_예외가_발생한다() {
        final String input = "a".repeat(256);

        assertThatThrownBy(() -> Name.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
