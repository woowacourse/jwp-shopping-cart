package cart.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NameTest {

    @ParameterizedTest
    @MethodSource("nameProvider")
    @DisplayName("상품을 정상적으로 등록할 수 있다")
    void nameTest(final String input) {
        assertDoesNotThrow(() -> Name.from(input));
    }

    static Stream<Arguments> nameProvider() {
        return Stream.of(
                Arguments.of("a"),
                Arguments.of("b".repeat(255))
        );
    }

    @DisplayName("상품명은 공백이나 null 값이 들어오면 예외가 발생한다")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @ParameterizedTest
    void throwExceptionWhenNameIsNotExist(final String input) {
        assertThatThrownBy(() -> Name.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품명의 최대 길이를 넘어서는 이름으로 등록하면 예외가 발생한다")
    @Test
    void throwExceptionWhenNameExceedLength() {
        final String input = "a".repeat(256);

        assertThatThrownBy(() -> Name.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
