package woowacourse.shoppingcart.domain.customer.privacy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class GenderTest {

    public static Stream<Arguments> parameter() {
        return Stream.of(
                Arguments.of("male", Gender.MALE),
                Arguments.of("female", Gender.FEMALE),
                Arguments.of("undefined", Gender.UNDEFINED)
        );
    }

    @DisplayName("성별에 대한 문자열을 전달하면 대응되는 객체를 반환한다.")
    @MethodSource("parameter")
    @ParameterizedTest
    void from(String input, Gender gender) {
        // when & then
        assertThat(Gender.from(input)).isEqualTo(gender);
    }

    @DisplayName("성별 객체에 대응되지 않는 문자열이 전달될 경우 예외가 발생한다.")
    @ValueSource(strings = {"", "fema", "mail"})
    @ParameterizedTest
    void from_invalidInput(String input) {
        // when & then
        assertThatThrownBy(() -> Gender.from(input))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("입력된 값에 해당하는 성별이 존재하지 않습니다.");
    }
}
